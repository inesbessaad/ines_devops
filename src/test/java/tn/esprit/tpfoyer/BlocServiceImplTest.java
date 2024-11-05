package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;
import tn.esprit.tpfoyer.service.BlocServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlocServiceImplTest {

    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private BlocServiceImpl blocService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllBlocs() {
        List<Bloc> blocs = List.of(new Bloc(), new Bloc());
        when(blocRepository.findAll()).thenReturn(blocs);

        List<Bloc> result = blocService.retrieveAllBlocs();

        assertEquals(2, result.size());
        verify(blocRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveBlocsSelonCapacite() {
        Bloc bloc1 = new Bloc();
        bloc1.setCapaciteBloc(50);
        Bloc bloc2 = new Bloc();
        bloc2.setCapaciteBloc(30);

        List<Bloc> blocs = List.of(bloc1, bloc2);
        when(blocRepository.findAll()).thenReturn(blocs);

        List<Bloc> result = blocService.retrieveBlocsSelonCapacite(40);

        assertEquals(1, result.size());
        assertEquals(50, result.get(0).getCapaciteBloc());
    }

    @Test
    void testRetrieveBloc() {
        Bloc bloc = new Bloc();
        bloc.setIdBloc(1L);
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));

        Bloc result = blocService.retrieveBloc(1L);

        assertNotNull(result);
        assertEquals(1L, result.getIdBloc());
        verify(blocRepository, times(1)).findById(1L);
    }

    @Test
    void testAddBloc() {
        Bloc bloc = new Bloc();
        when(blocRepository.save(bloc)).thenReturn(bloc);

        Bloc result = blocService.addBloc(bloc);

        assertNotNull(result);
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testModifyBloc() {
        Bloc bloc = new Bloc();
        bloc.setIdBloc(1L);
        when(blocRepository.save(bloc)).thenReturn(bloc);

        Bloc result = blocService.modifyBloc(bloc);

        assertNotNull(result);
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testRemoveBloc() {
        Long blocId = 1L;

        blocService.removeBloc(blocId);

        verify(blocRepository, times(1)).deleteById(blocId);
    }

    @Test
    void testTrouverBlocsSansFoyer() {
        List<Bloc> blocs = new ArrayList<>();
        when(blocRepository.findAllByFoyerIsNull()).thenReturn(blocs);

        List<Bloc> result = blocService.trouverBlocsSansFoyer();

        assertNotNull(result);
        assertEquals(blocs.size(), result.size());
        verify(blocRepository, times(1)).findAllByFoyerIsNull();
    }

    @Test
    void testTrouverBlocsParNomEtCap() {
        List<Bloc> blocs = new ArrayList<>();
        String nomBloc = "TestBloc";
        long capaciteBloc = 100;
        when(blocRepository.findAllByNomBlocAndCapaciteBloc(nomBloc, capaciteBloc)).thenReturn(blocs);

        List<Bloc> result = blocService.trouverBlocsParNomEtCap(nomBloc, capaciteBloc);

        assertNotNull(result);
        assertEquals(blocs.size(), result.size());
        verify(blocRepository, times(1)).findAllByNomBlocAndCapaciteBloc(nomBloc, capaciteBloc);
    }
}

