package ohtu.verkkokauppa;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class VerkkokauppaTest {

    public VerkkokauppaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void ostoksenPaatyttyaPankinMetodiaTilisiirtoKutsutaan() {
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(42);
        
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        
        Kauppa k = new Kauppa(varasto, pankki, viite);
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");
        
        verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 5);
    }
    
    @Test
    public void kahdellaSamallaOstoksellaTilisiirtoaKutsutaan() {
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(42);
        
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        
        Kauppa k = new Kauppa(varasto, pankki, viite);
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");
        
        verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 10);
    }
    
    @Test
    public void kahdellaEriOstoksellaTilisiirtoaKutsutaan() {
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(42);
        
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.saldo(2)).thenReturn(15);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "peruna", 1));
        
        Kauppa k = new Kauppa(varasto, pankki, viite);
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");
        
        verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 6);
    }
    
    @Test
    public void kahdellaEriOstoksellaJoistaToinenLoppuTilisiirtoaKutsutaan() {
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(42);
        
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.saldo(2)).thenReturn(0);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        
        Kauppa k = new Kauppa(varasto, pankki, viite);
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");
        
        verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 5);
    }

}