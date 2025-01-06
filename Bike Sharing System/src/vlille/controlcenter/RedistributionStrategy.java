package vlille.controlcenter;

/**
 * The RedistributionStrategy interface defines the contract for strategies
 * used to redistribute bikes across stations in the bike-sharing system.
 */

public interface RedistributionStrategy {


    /**
     * Redistributes bikes among stations based on the implemented strategy.
     *
     * @param controlCenter The control center managing the bike-sharing system.
     */
    
    void redistribute(ControlCenter controlCenter);

  
}