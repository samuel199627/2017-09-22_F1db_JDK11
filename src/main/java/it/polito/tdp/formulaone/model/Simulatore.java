package it.polito.tdp.formulaone.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import it.polito.tdp.formulaone.model.Evento.EventType;

public class Simulatore {
	
	
	int pausaBox;
	double probabilita;
	
	Map<Integer,DriverID> pilotiGara;
	Model m;
	PriorityQueue<Evento> coda;
	List<LapTime> passaggi;
	
	public Simulatore(Model model) {
		this.m=model;
	}
	
	public void init() {
		
		coda=new PriorityQueue<>();
		pilotiGara=new HashMap<>();
		this.pilotiGara=m.getPilotiGara();
		passaggi=new ArrayList<>();
		passaggi=m.getPassaggi();
		//ho i passaggi ordinati per giro crescente e quindi per ogni pilota posso generarmi tutti i suoi passaggi sul traguardo
		passaggi.sort(null);
		//dai laptime mi genero tutti gli eventi iniziali di passaggio associati ad ogni pilota
		//e analizzadoli posso o meno generare un passaggio al box
		//per ogni pilota inserisco solo i passaggi al primo giro
		System.out.println("\n\nPROBABILITA' PIT STOP: "+probabilita);
		System.out.println("TEMPO PAUSA BOX: "+pausaBox*1000+"\n\n");
		for(DriverID d: pilotiGara.values()) {
			//per un pilota aggiungo al massimo un giro
			boolean aggiunto=false;
			int tempoPilota=0;
			for(LapTime l:passaggi) {
				//ho un passaggio sul traguardo per il pilota che sto considerando
				if(l.getDriverId().equals(d)&&d.getLap()==(l.getLap()-1)&&aggiunto==false) {
					//in questo modo aggiungo un solo giro
					aggiunto=true;
					d.setLap(d.getLap()+1);
					tempoPilota=tempoPilota+l.getMiliseconds();
					coda.add(new Evento(tempoPilota,EventType.PASSAGGIO_TRAGUARDO,d));
					System.out.println("AGGIUNTO PASSAGGIO: "+d.getDriverID()+ " tempo "+tempoPilota);
					//controllo se il pilota alla fine del giro passera' primo
					if(l.getPosition()==1) {
						d.setPassaggiPrimo(d.getPassaggiPrimo()+1);
					}
					
				}
			}
		}
		
		
	}
	
	

	public Map<Integer, DriverID> getPilotiGara() {
		return pilotiGara;
	}

	public void setPilotiGara(Map<Integer, DriverID> pilotiGara) {
		this.pilotiGara = pilotiGara;
	}

	public void setPausaBox(int pausaBox) {
		this.pausaBox = pausaBox;
	}

	public void setProbabilita(double probabilita) {
		this.probabilita = probabilita;
	}

	public void run() {
		while(!coda.isEmpty()) {
			Evento e=coda.poll();
			ProcessEvent(e);
		}
	}
	
	public void ProcessEvent(Evento e) {
		DriverID d=e.getPilota();
		
		switch(e.getType()) {
		case PASSAGGIO_TRAGUARDO:
			
			//il passaggio sul traguardo suppongo che generi il passaggio di andare al box con una certa probabilita' all'inizio del giro successivo (la pausa)
			double rand=Math.random();
			
			if(rand<probabilita) {
				//d.setBox(true);
				//System.out.println("NUMERO CASUALE GENERATO: "+rand);
				coda.add(new Evento(e.getTime()+pausaBox*1000,EventType.PIT_STOP,d));
				System.out.println("AGGIUNTO PIT STOP: "+d.getDriverID());
			}
			else {
				//se non mi fermo al box con la pausa genero direttamente il passaggio prossimo sul giro
				boolean aggiunto=false;
				for(LapTime l:passaggi) {
					//ho un passaggio sul traguardo per il pilota che sto considerando
					//d.getLap() mi dice qual'e' l'ultimo giro che il pilota ha percorso e io devo prendere il passaggio sul traguardo che
					//e' indicizzato con l'indice successivo.
					//d.getLap() parte da 0 e dice che devo mettere il passaggio del giro 1 quindi il giro e' sfasato
					if(l.getDriverId().equals(d)&&d.getLap()==(l.getLap()-1)&&aggiunto==false) {
						aggiunto=true;
						d.setLap(d.getLap()+1);
						coda.add(new Evento(e.getTime()+l.getMiliseconds(),EventType.PASSAGGIO_TRAGUARDO,d));
						System.out.println("AGGIUNTO PASSAGGIO: "+d.getDriverID()+ " tempo "+(e.getTime()+l.getMiliseconds()));
						if(l.getPosition()==1) {
							d.setPassaggiPrimo(d.getPassaggiPrimo()+1);
						}
						
					}
				}
			}
			
			break;
			
		case PIT_STOP:
			//ho avuto un evento di pit stop e quindi il pilota e' pronto per avere l'evento del prossimo giro
			//aggiungo al massimo un giro per ogni pilota
			boolean aggiunto=false;
			for(LapTime l:passaggi) {
				//ho un passaggio sul traguardo per il pilota che sto considerando
				//d.getLap() mi dice qual'e' l'ultimo giro che il pilota ha percorso e io devo prendere il passaggio sul traguardo che
				//e' indicizzato con l'indice successivo.
				//d.getLap() parte da 0 e dice che devo mettere il passaggio del giro 1 quindi il giro e' sfasato
				if(l.getDriverId().equals(d)&&d.getLap()==(l.getLap()-1)&&aggiunto==false) {
					aggiunto=true;
					d.setLap(d.getLap()+1);
					coda.add(new Evento(e.getTime()+l.getMiliseconds(),EventType.PASSAGGIO_TRAGUARDO,d));
					System.out.println("AGGIUNTO PASSAGGIO: "+d.getDriverID()+ " tempo "+(e.getTime()+l.getMiliseconds()));
					if(l.getPosition()==1) {
						d.setPassaggiPrimo(d.getPassaggiPrimo()+1);
					}
					
				}
			}
			break;
			
		}
	}
	
}
