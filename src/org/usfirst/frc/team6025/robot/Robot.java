package org.usfirst.frc.team6025.robot;


import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Solenoid;

public class Robot extends IterativeRobot {
	Solenoid elac = new Solenoid(0);
	Solenoid elkapa = new Solenoid(1);

	Victor kolcuk = new Victor(9);
	Talon sagrampa = new Talon(4);
	Talon sagrampa2 = new Talon(5);
	Victor solrampa = new Victor(7);
	Talon solrampa2 = new Talon(6);


	int gidis;
	int otonomkol;
	int kol;

	RobotDrive AArobot;  
	Joystick solStick; 

 Joystick xbox;

	Joystick sagStick;
	/*
	 * 
	 * Robot
	 * Isim atamalari
	 */
   
    public void robotInit() {
    	AArobot = new RobotDrive(0,1,2,3);
		solStick = new Joystick(0);
		sagStick = new Joystick(1);
 xbox = new Joystick(2);

    }
    

    public void autonomousInit() {
    	gidis = 1;
    	otonomkol = 1;
     AArobot.setMaxOutput(0.4);
    	
    }

   /*
    * Otonom 
    */
    public void autonomousPeriodic() {
    	if(otonomkol < 10)
		  {
			  otonomkol++;
			  kolcuk.set(1);
		  }
			  else {
				  
		
	    	if(gidis < 86) 
			{
				AArobot.drive(-0.5, 0.0); 	
				gidis++;
				} else 		{
				elac.set(true);
				elkapa.set(false);
				AArobot.drive(0.5, 0.0); 	
				gidis--;
			kolcuk.set(0.05)
				  
				
				}
				if(gidis = 0)
				{
					AArobot.drive(0.0, 0.0); 	
					kolcuk.set(0)
				}
					
			  }
					
			
			}
		
    
    

    public void teleopInit(){
    	kol=0;
    }

 
    public void teleopPeriodic() {

		AArobot.setSafetyEnabled(true);
		kolcuk.setSafetyEnabled(false); 
		sagrampa.setSafetyEnabled(false);
		sagrampa2.setSafetyEnabled(false);
		while (isOperatorControl() && isEnabled()) {
			AArobot.tankDrive(solStick, sagStick);
			/*
			 * Simit kolunun kalkis ve inis tus atamalari
			 */
			AArobot.setMaxOutput(0.4);
			if (sagStick.getRawButton(4)) {
				if(kol < 4)
				  {
					  otonomkol++; //Simiti daha iyi kavrayabilmesi için kaldırdığı anda simiti sıkıştırmak için(otomatik yapması için. 124.satırdan sonraki kodlarda ise manuel yapmaya yarıyor.) 
					  kolcuk.set(1);
				  }
					  else{
						  elac.set(false);
						elkapa.set(true);
							kol=0;
						  
				  }

			}
			if (sagStick.getRawButton(1)) {
				kolcuk.set(-0.2);

			}
			else if (solStick.getRawButton(1)) {
				kolcuk.set(1);

			}
			else {
				kolcuk.set(0.2);
			}
			/*
			 * Sag rampanin milini kontrol eden motor
			 */   
			if (xbox.getRawButton(1)) {
				sagrampa.set(1);	
				sagrampa2.set(1);
			}
			else if (xbox.getRawButton(2)) {
				sagrampa.set(-1);
				sagrampa2.set(-1);
			}      
			else{
				sagrampa.set(0);	
				sagrampa2.set(0);
			}

			if (xbox.getRawButton(3)){
				solrampa.set(-1);
				solrampa2.set(-1);
			}else if (xbox.getRawButton(4)){
				solrampa.set(1);
				solrampa2.set(1);
			}
			else{
				solrampa.set(0);
				solrampa2.set(0);
			}


			/*
			 * Nitro
			 */
			if (solStick.getRawButton(2)) {
				AArobot.setMaxOutput(0.8); 
			}
			/*
			 * Pnomatic ile gripper haraketleri
			 */
			// Piston Acma
			if (sagStick.getRawButton(3)) {
				elac.set(false);
				elkapa.set(true);
			} else if (solStick.getRawButton(4)) {
				elac.set(true);
				elkapa.set(false);
			} else {
				elkapa.set(false);
				elac.set(false);
			}

		}
	}
    
    
   
    public void testPeriodic() {
    
    }
}

