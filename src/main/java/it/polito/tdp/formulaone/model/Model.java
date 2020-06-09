package it.polito.tdp.formulaone.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {
	
	FormulaOneDAO dao;
	Map<Integer, Race> gareAnno;
	SimpleWeightedGraph<Race,DefaultWeightedEdge> grafo;
	List<Adiacenza> adiacenze;
	
	public Model() {
		dao=new FormulaOneDAO();
	}
	
	
	public String creaGrafo(Season s) {
		gareAnno=new HashMap<>();
		gareAnno=dao.importaGareAnno(s);
		
		grafo=new SimpleWeightedGraph<Race,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, gareAnno.values());
		
		System.out.println("GRAFO CREATO CON VERTICI IN NUMERO: "+grafo.vertexSet().size());
		
		adiacenze=new ArrayList<>();
		adiacenze=dao.importaAdiacenze(gareAnno);
		int pesoMax=0;
		List<Adiacenza> archiPesoMassimo=new ArrayList<>();
		
		for(Adiacenza a: adiacenze) {
			Graphs.addEdge(grafo, a.getR1(), a.getR2(), a.getPeso());
			if(a.getPeso()>pesoMax) {
				//System.out.println("Abbiamo un nuovo peso massimo! che vale"+a.getPeso());
				pesoMax=a.getPeso();
				archiPesoMassimo=new ArrayList<>();
				archiPesoMassimo.add(a);
			}
			else if(a.getPeso()==pesoMax) {
				archiPesoMassimo.add(a);
			}
		}
		
		System.out.println("GRAFO CREATO CON ARCHI IN NUMERO: "+grafo.edgeSet().size());
		String ritorna="ECCO GLI ARCHI DI PESO MASSIMO: (peso massimo="+pesoMax+") \n\n";
		for(Adiacenza a: archiPesoMassimo) {
			ritorna=ritorna+a.getR1().getName()+" - "+a.getR2().getName()+" con peso "+a.getPeso()+" \n";
		}
		
		return ritorna;
	}
	
	public List<Season> importaStagioni(){
		return dao.getAllSeasons();
	}
	
	public List<Race> ritornaVertici(){
		List<Race> gareVertici=new ArrayList<>();
		for(Race r: grafo.vertexSet()) {
			gareVertici.add(r);
		}
		gareVertici.sort(null);
		return gareVertici;
	}
	
	//mi faccio dare tutti i piloti che hanno partecipato ad una data gara selezionata perche' poi ne devo fare una classifica
	Map<Integer,DriverID> pilotiGara;
	List<LapTime> passaggi;
	public String simula(Race r, double probabilita, int tempoSecondi) {
		pilotiGara=new HashMap<>();
		pilotiGara=dao.importaPilotiGara(r);
		System.out.println("PILOTI IMPORTATI PER LA GARA: (in numero "+pilotiGara.size()+")");
		for(DriverID d: pilotiGara.values()) {
			System.out.println("pilota->"+d.getDriverID());
		}
		passaggi=new ArrayList<>();
		passaggi=dao.importaPassaggiGara(r, pilotiGara);
		
		Simulatore sim=new Simulatore(this);
		
		sim.setPausaBox(tempoSecondi);
		sim.setProbabilita(probabilita);
		sim.init();
		sim.run();
		/*
		Map<Integer, DriverID> pilotiPostGara=new HashMap<>();
		pilotiPostGara=sim.getPilotiGara();
		*/
		String ritorna="ANDAMENTO DELLA GARA: \n\n";
		for(DriverID d: pilotiGara.values()) {
			ritorna=ritorna+"Il pilota "+d.getDriverID()+" e' passato primo sul traguardo un numero di volte "+d.getPassaggiPrimo()+"\n";
		}
		
		return ritorna;
	}


	public List<LapTime> getPassaggi() {
		return passaggi;
	}


	public Map<Integer, DriverID> getPilotiGara() {
		return pilotiGara;
	}


	
	
	
	
	
	
	

}
