package org.usfirst.frc.team6025.robot;


import edu.wpi.first.wpilibj.RobotDrive; //robotun tekerleklerini tek tek tanimlamak istemiyorsanız robot drive kütüphanesi içe aktarma.
import edu.wpi.first.wpilibj.IterativeRobot; //robota kodu sürekli calistirabilmek için.
import edu.wpi.first.wpilibj.Joystick; // robotu kumanda edebilmeniz icin (xboxlar icinde joystick girmeniz gerekmektedir. xbox diye wpilibin kütüphanesi yoktur.
import edu.wpi.first.wpilibj.Talon; //talon motor sürücüleri 
import edu.wpi.first.wpilibj.Victor; // Victor motor sürücüleri (Victor sp için VictorSP kütüphanesini eklemeniz gerekmektedir.)
import edu.wpi.first.wpilibj.Solenoid; // pnomatige komut gönderebilmek için selenoidlerin kütüphanesi.
import edu.wpi.first.wpilibj.DigitalInput; // limit switch icin digital girisleri okumamıza yarayan kütüphane

public class Robot extends IterativeRobot {
	Solenoid elac = new Solenoid(0); //pcm üzerindeki numaralara göre yapılmaktadi.
	Solenoid elkapa = new Solenoid(1);

	Victor kolcuk = new Victor(9); // PWM numaralari
	Talon sagrampa = new Talon(4);
	Talon sagrampa2 = new Talon(5);
	Victor solrampa = new Victor(7);
	Talon solrampa2 = new Talon(6);
        DigitalInput durdurac = new DigitalInput(0);

	int gidis; // integer atmak yani tamsayi tanimlamak icin kullanilir.
	int otonomkol;
	int kol;

	RobotDrive AArobot;  // robotdrive kütüphanesini AArobot olarak tanimliyoruz.
	Joystick solStick;  // 3 joystick kullandik biz 1. joystick sol motorlari kontrol ediyor.

 Joystick xbox; // xbox fonksiyonlar için kullandik.

	Joystick sagStick; // sag motorlari kontrol eden joystick
	/*
	 * 
	 * Robot
	 * Isim atamalari
	 */
   
    public void robotInit() {
    	AArobot = new RobotDrive(0,1,2,3); //Drive pwmlerinin tanımlanması.
		solStick = new Joystick(0); // joysticklerin bağlı olduğu slotların numaralarının tanımlanması.
		sagStick = new Joystick(1);
 xbox = new Joystick(2);

    }
    

    public void autonomousInit() {
    	gidis = 1; //Başlangıç değerlerinin artması
    	otonomkol = 1;
     AArobot.setMaxOutput(0.4); //robotun 1 üzerinden max hızı
    	
    }

   /*
    * Otonom 
    */
    public void autonomousPeriodic() {
    	if(otonomkol < 10) // eğer otonomkol değeri 10un altındaysa aşağıdakileri yap.
		  {
			  otonomkol++; // kendisini sürekli loopa aldığı için her kod başa döndüğünde otonomkol değerini 1 arttırıyor.
			  kolcuk.set(1); // değer 10 olana kadar kolu 1 hızında kaldırıyor. 
		  }
			  else { // 
			  }
		
	    	if(gidis < 86) // eğer gisi değer 86 'dan küçükse aşağıdakileri yap.
			{
				AArobot.drive(-0.5, 0.0); 	// robotu 0.5 hızla ileri sür. değer 86 olana kadar yaklaşık 1.5 saniye felan haraket ediyor robot.
				gidis++; // her loop tekrarlandığında loop değerinin artması
				} else 		{ // 86 dan küçük değilse
				elac.set(true); //
				elkapa.set(false); // pnomatik 2 hava kanalına bağlı olduğu için bir hava kanalını 72. satırda açıyoruz diğer hava kanalını burda kapıyoruz böylece kolumuz simiti bırakıyor.
				AArobot.drive(0.5, 0.0); // robotu tekrar  başlangıç noktasına sürmek.
				gidis--; // gidisi her loopta -1 azaltmak.
			kolcuk.set(0.05) // simit kolunun hızı 
				  
				
				}
				if(gidis = 0) // eger gidis 0'a esit ise asagidakileri yap
				{
					AArobot.drive(0.0, 0.0); 	//robotu durdur.
					kolcuk.set(0) //robot simit kolunu durdur.
				}
					
			  }
					
			
			}
		
    
    

    public void teleopInit(){
    	kol=0; // kol baslangic degeri 0 
    }

 
    public void teleopPeriodic() {

		AArobot.setSafetyEnabled(true); //AA robotun guvenligi acik yani eger robotun tekerleklerinde herhangi bir sikinti oldugunda driver station robotu disabled yapacak.
		kolcuk.setSafetyEnabled(false); 
		sagrampa.setSafetyEnabled(false);
		sagrampa2.setSafetyEnabled(false);
		while (isOperatorControl() && isEnabled()) { // operator (driver station) enable oldugunda 
			AArobot.tankDrive(solStick, sagStick);
			/*
			 * Simit kolunun kalkis ve inis tus atamalari
			 */
			AArobot.setMaxOutput(0.4); // robotun max hizi
			if (sagStick.getRawButton(4)) { // eger joystickten 4. butona basilirsa asagidakileri yap
				if(kol < 4) // kol 4'den kucuk ise 
				  {
					  otonomkol++; //Simiti daha iyi kavrayabilmesi için kaldirdigi anda simiti sikiştirmak icin(otomatik yapmasi icin. 124.satirdan sonraki kodlarda ise manuel yapmaya yariyor)
					  kolcuk.set(1); // kolu 1 hızında calistir
				  }
					  else{
						  elac.set(false); //piston kapa
						elkapa.set(true);
							kol=0; // kol degerini sifirla
						  
				  }

			}
			if(durdurac.get()) //Stopper robotun kolu belirli bir yere kadar kalktiginda switch tarafından algilandiginda kendisini durdurması icin.
                          {
                                kolcuk.set(0.0); // kolu durdur.
                          }
			else if (sagStick.getRawButton(1)) { // joystickten 1. butona basilirsa
				kolcuk.set(-0.2); // kolu ters yöne dogru calistir.

			}
			else if (solStick.getRawButton(1)) { // diger joystickten 1. butona basilirsa
				kolcuk.set(1); // kolu 1 hizinda kaldir.

			}
			else {
				kolcuk.set(0.2); // kolun backdrive atmamasiiçin
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
			if (solStick.getRawButton(2)) { // sol joystickten 2. butona basilirsa
				AArobot.setMaxOutput(0.8);  // robotun hizini 0.8 yap
			}
			/*
			 * Pnomatic ile gripper haraketleri
			 */
			// Piston Acma
			if (sagStick.getRawButton(3)) {
				elac.set(false);
				elkapa.set(true);
			} else if (solStick.getRawButton(4)) {
				elac.set(true); // piston kapama 
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

