package de.gothaer.bestelldemo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.gothaer.bestelldemo.repositories.BestellRepository;
import de.gothaer.bestelldemo.repositories.model.Bestellung;

@ExtendWith(MockitoExtension.class)
public class BestellServiceImplTest {

	private static final Bestellung VALID_ORDER = new Bestellung();
	private static final double VALID_SALDO = 10.0;
	private static final String VALID_MASTERCARD = "M0123456789";
	private static final String VALID_VISACARD = "V0123456789";

	@InjectMocks
	private BestellServiceImpl objectUnderTest;
	@Mock
	private BestellRepository repoMock;
	@Mock
	private SolvenzService solvenzServiceMock;

	@Nested
	@DisplayName("Prüfen, ob parameter null sind")
	class NullParameter {
		@Test
		void bestellen_BestellungNull_throwsBestellServiceException() throws Exception {
			BestellServiceException ex = assertThrows(BestellServiceException.class,
					() -> objectUnderTest.erfasseBestellung(null, VALID_MASTERCARD, VALID_SALDO));
			assertEquals("Bestellung darf nicht null sein", ex.getMessage());
		}

		@Test
		void bestellen_WrongCreditCardFormatNull_throwsBestellServiceException() throws Exception {
			BestellServiceException ex = assertThrows(BestellServiceException.class,
					() -> objectUnderTest.erfasseBestellung(VALID_ORDER, null, VALID_SALDO));
			assertEquals("Kreditkarte darf nicht null sein.", ex.getMessage());
		}
	}

	@Nested
	@DisplayName("Kartenformat")
	class Kartenformat {
		@Test
		@DisplayName("Karl Peter Schmitt")
		void bestellen_WrongCreditCardFormattooShort_throwsBestellServiceException() throws Exception {
			BestellServiceException ex = assertThrows(BestellServiceException.class,
					() -> objectUnderTest.erfasseBestellung(VALID_ORDER, "M012345678", VALID_SALDO));
			assertEquals("Falsches Kartenformat.", ex.getMessage());
		}

		@Test
		void bestellen_WrongCreditCardFormattooLong_throwsBestellServiceException() throws Exception {
			BestellServiceException ex = assertThrows(BestellServiceException.class,
					() -> objectUnderTest.erfasseBestellung(VALID_ORDER, "M01234567890", VALID_SALDO));
			assertEquals("Falsches Kartenformat.", ex.getMessage());
		}

		@Test
		void bestellen_WrongCreditCardFormatNeitherVisaNorMaster_throwsBestellServiceException() throws Exception {
			BestellServiceException ex = assertThrows(BestellServiceException.class,
					() -> objectUnderTest.erfasseBestellung(VALID_ORDER, "X0123456789", VALID_SALDO));
			assertEquals("Falsches Kartenformat.", ex.getMessage());
		}
	}

	@Nested
	@DisplayName("Saldoprüfung")
	class Saldo {
		@Test
		void bestellen_AmountNegative_throwsBestellServiceException() throws Exception {
			BestellServiceException ex = assertThrows(BestellServiceException.class,
					() -> objectUnderTest.erfasseBestellung(VALID_ORDER, VALID_MASTERCARD, -10.0));
			assertEquals("Saldo darf nicht negativ sein.", ex.getMessage());
		}
	}

	@Nested
	@DisplayName("Solvenzprüfung")
	class SolvenzPruefung {
		@Test
		void bestellen_KundeInsolvent_throwsKundeIstPleiteException() throws Exception {
			when(solvenzServiceMock.checkSolvenz(anyString(), anyString(), anyDouble())).thenReturn(false);
			KundeIstPleiteException ex = assertThrows(KundeIstPleiteException.class,
					() -> objectUnderTest.erfasseBestellung(VALID_ORDER, VALID_MASTERCARD, VALID_SALDO));
			assertEquals("Kunde ist nicht solvent.", ex.getMessage());
		}

		@Test
		void bestellen_CreditcardServerDown_throwsBestellServiceException() throws Exception {
			when(solvenzServiceMock.checkSolvenz(anyString(), anyString(), anyDouble()))
					.thenThrow(new SolvenzServiceException("upps"));
			BestellServiceException ex = assertThrows(BestellServiceException.class,
					() -> objectUnderTest.erfasseBestellung(VALID_ORDER, VALID_MASTERCARD, VALID_SALDO));
			assertEquals("Solvenzservice antwortet nicht.", ex.getMessage());
		}

		@Test
		void bestellen_SolvenzServiceCall_ParametersPassed() throws Exception {
			when(solvenzServiceMock.checkSolvenz(anyString(), anyString(), anyDouble())).thenReturn(true);
			objectUnderTest.erfasseBestellung(VALID_ORDER, VALID_MASTERCARD, VALID_SALDO);

			verify(solvenzServiceMock).checkSolvenz("MASTER", "0123456789", VALID_SALDO);
		}
	}

	@Nested
	@DisplayName("Speichern im Repo")
	class RepoPrüfung {
		@Test
		void bestellen_RepoDown_throwsBestellserviceException() throws Exception {
			when(solvenzServiceMock.checkSolvenz(anyString(), anyString(), anyDouble())).thenReturn(true);
			doThrow(new ArrayIndexOutOfBoundsException()).when(repoMock).save(any(Bestellung.class));
			BestellServiceException ex = assertThrows(BestellServiceException.class,
					() -> objectUnderTest.erfasseBestellung(VALID_ORDER, VALID_MASTERCARD, VALID_SALDO));
			assertEquals("Fehler im Service.", ex.getMessage());
		}

		@Test
		void bestellen_HappyDayMaster_BestellungPassedToRepo() throws Exception {
			when(solvenzServiceMock.checkSolvenz(anyString(), anyString(), anyDouble())).thenReturn(true);
			doNothing().when(repoMock).save(any(Bestellung.class));

			objectUnderTest.erfasseBestellung(VALID_ORDER, VALID_MASTERCARD, VALID_SALDO);

			InOrder inOrder = inOrder(solvenzServiceMock, repoMock);
			inOrder.verify(solvenzServiceMock).checkSolvenz("MASTER", "0123456789", VALID_SALDO);
			inOrder.verify(repoMock).save(VALID_ORDER);

		}

		@Test
		void bestellen_HappyDayVisa_BestellungPassedToRepo() throws Exception {
			when(solvenzServiceMock.checkSolvenz(anyString(), anyString(), anyDouble())).thenReturn(true);
			doNothing().when(repoMock).save(any(Bestellung.class));

			objectUnderTest.erfasseBestellung(VALID_ORDER, VALID_VISACARD, VALID_SALDO);

			InOrder inOrder = inOrder(solvenzServiceMock, repoMock);
			inOrder.verify(solvenzServiceMock).checkSolvenz("VISA", "0123456789", VALID_SALDO);
			inOrder.verify(repoMock).save(VALID_ORDER);

		}
	}

}
