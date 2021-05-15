package hu.bme.mit.spaceship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class GT4500Test {

    private GT4500 ship;

    private TorpedoStore primaryTorpedoStore;

    private TorpedoStore secondaryTorpedoStore;

    @BeforeEach
    public void init() {
        this.primaryTorpedoStore = mock(TorpedoStore.class);
        this.secondaryTorpedoStore = mock(TorpedoStore.class);

        this.ship = new GT4500(this.primaryTorpedoStore, this.secondaryTorpedoStore);
    }

    @Test
    public void fireTorpedo_Single_Success() {
        // Arrange
        when(this.primaryTorpedoStore.isEmpty()).thenReturn(true);
        when(this.secondaryTorpedoStore.isEmpty()).thenReturn(false);

        when(this.primaryTorpedoStore.fire(1)).thenReturn(true);
        when(this.secondaryTorpedoStore.fire(1)).thenReturn(true);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);

        // Assert
        verify(this.secondaryTorpedoStore, times(1)).fire(1);
        assertTrue(result);
    }

    @Test
    public void fireTorpedo_All_Success() {
        // Arrange
        when(this.primaryTorpedoStore.isEmpty()).thenReturn(false);
        when(this.secondaryTorpedoStore.isEmpty()).thenReturn(false);

        when(this.primaryTorpedoStore.fire(1)).thenReturn(true);
        when(this.secondaryTorpedoStore.fire(1)).thenReturn(true);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.ALL);

        // Assert
        verify(this.primaryTorpedoStore, times(1)).fire(1);
        verify(this.secondaryTorpedoStore, times(1)).fire(1);

        assertTrue(result);
    }

    @Test
    public void fireTorpedo_firstTimePrimaryFired() {
        when(this.primaryTorpedoStore.isEmpty()).thenReturn(false);
        when(this.secondaryTorpedoStore.isEmpty()).thenReturn(false);

        when(this.primaryTorpedoStore.fire(1)).thenReturn(true);
        when(this.secondaryTorpedoStore.fire(1)).thenReturn(true);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);

        // Assert
        verify(this.primaryTorpedoStore, times(1)).fire(1);
        assertTrue(result);
    }

    @Test
    public void fireTorpedo_Alternating() {
        when(this.primaryTorpedoStore.isEmpty()).thenReturn(false);
        when(this.secondaryTorpedoStore.isEmpty()).thenReturn(false);

        when(this.primaryTorpedoStore.fire(1)).thenReturn(true);
        when(this.secondaryTorpedoStore.fire(1)).thenReturn(true);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE) && ship.fireTorpedo(FiringMode.SINGLE);

        // Assert
        verify(this.primaryTorpedoStore, times(1)).fire(1);
        verify(this.secondaryTorpedoStore, times(1)).fire(1);
        assertTrue(result);
    }

    @Test
    public void fireTorpedo_PrimaryTwice() {
        when(this.primaryTorpedoStore.isEmpty()).thenReturn(false);
        when(this.secondaryTorpedoStore.isEmpty()).thenReturn(true);

        when(this.primaryTorpedoStore.fire(1)).thenReturn(true);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE) && ship.fireTorpedo(FiringMode.SINGLE);

        // Assert
        verify(this.primaryTorpedoStore, times(2)).fire(1);
        verify(this.secondaryTorpedoStore, times(0)).fire(1);
        assertTrue(result);
    }

    @Test
    public void fireTorpedo_FiringFailed() {
        when(this.primaryTorpedoStore.isEmpty()).thenReturn(false);
        when(this.secondaryTorpedoStore.isEmpty()).thenReturn(false);

        when(this.primaryTorpedoStore.fire(1)).thenReturn(false);
        when(this.secondaryTorpedoStore.fire(1)).thenReturn(true);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);

        // Assert
        verify(this.primaryTorpedoStore, times(1)).fire(1);
        verify(this.secondaryTorpedoStore, times(0)).fire(1);
        assertFalse(result);
    }

    @Test
    public void fireTorpedo_AllFiringFailedBecauseBothEmpty() {
        when(this.primaryTorpedoStore.isEmpty()).thenReturn(true);
        when(this.secondaryTorpedoStore.isEmpty()).thenReturn(true);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.ALL);

        // Assert
        assertFalse(result);
    }

    @Test
    public void fireLaser_Success() {
        boolean result = ship.fireLaser(FiringMode.ALL);
        assertFalse(result);
    }
}
