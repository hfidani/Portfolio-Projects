package vlille.bike;

import vlille.controlcenter.* ;

import java.util.TimerTask;

import exceptions.BikeNotRepairableException;

import java.util.Timer;

public class Reparator implements Service{
    
    
    @Override
    public void ControlService(Bike bike) throws BikeNotRepairableException{
        
    	if (! (bike.getState() instanceof OutOfService)) {
    		throw new BikeNotRepairableException("non reparable");
    	}
        System.out.println("Réparation du vélo en cours...");

        // Planifier la tâche de remise en service après 1min
        Timer repairTimer = new Timer();
        repairTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Changer l'état du vélo à InService après l'intervalle
                bike.setState(new UnderReparation());
                System.out.println("Réparation terminée. Le vélo est maintenant en service.");
                // Annuler le timer après la tâche
                repairTimer.cancel();
            }
        },   3 * 100);  
        bike.setState(new InService());
    }
    
}
