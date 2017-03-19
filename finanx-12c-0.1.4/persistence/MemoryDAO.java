package persistence;

import memory.HP12CFinanceMemory;
import memory.HP12CGeneralMemory;
import memory.HP12CProgramMemory;
import memory.HP12CStack;
import memory.HP12CStep;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.HP12CConfiguration;

public class MemoryDAO extends DataAccessObject{
	
	private HP12CStack stk;
	private HP12CFinanceMemory fin;
	private HP12CGeneralMemory mem;
	private HP12CProgramMemory prg;
	private HP12CStep stp;
	
	private Element de;
	
	public MemoryDAO(HP12CConfiguration cfg){
		super.manager = DOMManager.getManager();
		super.eName=DataAccessObject.MEMORY;
		super.path = "data/" + eName + ".xml";
		super.dom = manager.getDOM(path, false);

		this.stk = new HP12CStack(cfg.getStackSize());
		this.fin = new HP12CFinanceMemory();
		this.mem = new HP12CGeneralMemory(cfg.getMemorySize());
		this.prg = new HP12CProgramMemory(cfg.getProgramSize());
		
		de = dom.getDocumentElement();
		createMemory(de);
	}
	
	public HP12CStack getStack(){
		if(de!=null){
			return stk;
		}
		return null;	
	}
	
	public HP12CFinanceMemory getFinanceMemory(){
		if(de!=null){
			return fin;
		}
		return null;	
	}
	
	public HP12CGeneralMemory getGeneralMemory(){
		if(de!=null){
			return mem;
		}
		return null;	
	}
	
	public HP12CProgramMemory getProgramMemory(){
		if(de!=null){
			return prg;
		}
		return null;	
	}
	
	public void setStack(HP12CStack stk){
		this.stk = stk;
	}
	
	public void setFinanceMemory(HP12CFinanceMemory fin){
		this.fin = fin;
	}
	
	public void setGegeralMemory(HP12CGeneralMemory mem){
		this.mem = mem;
	}
	
	public void setProgramMemory(HP12CProgramMemory prg){
		this.prg = prg;
	}
	
	private void createMemory(Element e){
		
		NodeList nl = null;
		Element tag = null;
		 
		String p0 = "";
		String p1 = "";
		String p2 = "";
		
		try{
			for(int i=0; i<this.stk.getSize(); i++){
				nl = e.getElementsByTagName("stk"+i);
	
				if((nl.getLength()>0) && (nl!=null)){
					tag = (Element)nl.item(0);
					
					if((tag.getAttribute("p0")!=null)&&(tag!=null)){
						p0 = tag.getAttribute("p0");
						stk.set(i, Double.parseDouble(p0));
					}
				}
			}
	
			for(int i=0; i<this.fin.getSize(); i++){
				nl = e.getElementsByTagName("fin"+i);
	
				if((nl.getLength()>0) && (nl!=null)){
					tag = (Element)nl.item(0);
					
					if((tag.getAttribute("p0")!=null)&&(tag!=null)){
						p0 = tag.getAttribute("p0");
						fin.set(i, Double.parseDouble(p0));
					}
				}
			}
			
			for(int i=0; i<this.mem.getSize(); i++){
				nl = e.getElementsByTagName("mem"+i);
	
				if((nl.getLength()>0) && (nl!=null)){
					tag = (Element)nl.item(0);
					
					if((tag.getAttribute("p0")!=null)&&(tag!=null)){
						p0 = tag.getAttribute("p0");
						mem.set(i, Double.parseDouble(p0));
					}
					if((tag.getAttribute("p1")!=null)&&(tag!=null)){
						p1 = tag.getAttribute("p1");
						mem.setTimes(i, Double.parseDouble(p1));
					}
				}
			}
			
			for(int i=0; i<this.prg.getSize(); i++){
				nl = e.getElementsByTagName("prg"+i);
				
				stp = new HP12CStep();
				
				if((nl.getLength()>0) && (nl!=null)){
					tag = (Element)nl.item(0);
					
					if((tag.getAttribute("p0")!=null)&&(tag!=null)){
						p0 = tag.getAttribute("p0");
						prg.setModifier(i, Integer.parseInt(p0));
					}
					if((tag.getAttribute("p1")!=null)&&(tag!=null)){
						p1 = tag.getAttribute("p1");
						prg.setKey(i, Integer.parseInt(p1));
					}
					if((tag.getAttribute("p2")!=null)&&(tag!=null)){
						p2 = tag.getAttribute("p2");
						prg.setKey(i, Integer.parseInt(p2));
					}
					
					prg.set(i, stp);
				}
			}
		}
		catch(Exception except){
			System.out.print("[ParseError] An error ocurred while loading configurations.");
			System.out.print("[ParseError] The default configurations will be assumed.");
		}

	}	
	public void save(){
		
		Element de = dom.getDocumentElement();
		
		NodeList lisRm = de.getChildNodes();
		Node eRm = null;
		
		Element eAdd = null;
		
		for(int i=0; i<lisRm.getLength(); i++){
			eRm = lisRm.item(i);
			if(eRm!=null){de.removeChild(eRm);}
		}

		for(int i=0; i<this.stk.getSize(); i++){
			lisRm = de.getElementsByTagName("stk"+i);
			eRm = (Element)lisRm.item(0);
			if(eRm!=null){de.removeChild(eRm);}
		}
		
		for(int i=0; i<this.fin.getSize(); i++){
			lisRm = de.getElementsByTagName("fin"+i);
			eRm = (Element)lisRm.item(0);
			if(eRm!=null){de.removeChild(eRm);}
		}
		
		for(int i=0; i<this.mem.getSize(); i++){
			lisRm = de.getElementsByTagName("mem"+i);
			eRm = (Element)lisRm.item(0);
			if(eRm!=null){de.removeChild(eRm);}
		}
		
		for(int i=0; i<this.prg.getSize(); i++){
			lisRm = de.getElementsByTagName("prg"+i);
			eRm = (Element)lisRm.item(0);
			if(eRm!=null){de.removeChild(eRm);}
		}
		
		for(int i=0; i<this.stk.getSize(); i++){
				eAdd = dom.createElement("stk"+i);
				eAdd.setAttribute("p0", stk.get(i)+"");
				if(eAdd!=null){ de.appendChild(eAdd); }
		}
		
		for(int i=0; i<this.fin.getSize(); i++){
			eAdd = dom.createElement("fin"+i);
			eAdd.setAttribute("p0", fin.get(i)+"");
			if(eAdd!=null){ de.appendChild(eAdd); }
		}
		
		for(int i=0; i<this.mem.getSize(); i++){
			eAdd = dom.createElement("mem"+i);
			eAdd.setAttribute("p0", mem.get(i)+"");
			eAdd.setAttribute("p1", mem.getTimes(i)+"");
			if(eAdd!=null){ de.appendChild(eAdd); }
		}
		
		for(int i=0; i<this.prg.getSize(); i++){
			eAdd = dom.createElement("prg"+i);
			eAdd.setAttribute("p0", prg.getModifier(i)+"");
			eAdd.setAttribute("p1", prg.getKey(i)+"");
			eAdd.setAttribute("p2", prg.getComplement(i)+"");
			if(eAdd!=null){ de.appendChild(eAdd); }
		}
		
		manager.saveDOM(path);

	}
}
