/**
 * This file is part of fr.inria.plasmalab.ui.
 *
 * fr.inria.plasmalab.ui is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.ui is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.ui.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.ui.structure;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.controler.Controler;
import fr.inria.plasmalab.text_data.TextDataFactory;
import fr.inria.plasmalab.ui.controler.ProjectControler;
import fr.inria.plasmalab.ui.tabs.edition.observer.ProjectObserver;
import fr.inria.plasmalab.workflow.data.AbstractData;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.factory.AbstractDataFactory;
import fr.inria.plasmalab.workflow.data.factory.AbstractModelFactory;
import fr.inria.plasmalab.workflow.data.factory.AbstractRequirementFactory;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaPluginNotFound;

/**
 * 
 * @author kevin.corre@inria.fr
 * 
 * The Project class is the data structure of Plasma Lab GUI.
 * 
 * A Project is made of a {@link List} of {@link AbstractModel} and a List of {@link AbstractRequirement}.</p>
 * AbstractRequirement can be attached to an AbstractModel.</p>
 * There may be several Project in one instance of Plasma Lab GUI.
 *
 */
public class Project {
	public static final String nameAtt="name", typeAtt = "type", modelCn="models", modelElt="model", 
			reqCn="requirements", reqElt="requirement", resCn="resources", resElt="resource", contc="content", filec="file";
	final static Logger logger = LoggerFactory.getLogger(Project.class);
	
	
	private List<AbstractData> amList;
	private List<AbstractData> arList;
	private List<AbstractData> adList;
	private List<ProjectObserver> observers;

	private String name;
	private File file;
	
	private boolean empty, saved;
	
	private ProjectControler pControler;

	public Project (String name, ProjectControler pControler){
		this.name=name;
		this.pControler = pControler;
		this.amList = new ArrayList<AbstractData>();
		this.arList = new ArrayList<AbstractData>();
		this.adList = new ArrayList<AbstractData>();
		this.observers = new ArrayList<ProjectObserver>();
		empty = true;
		saved = true;
	}

	public Project(File target, ProjectControler pControler) throws JDOMException, IOException {
		this.pControler = pControler;
		this.amList = new ArrayList<AbstractData>();
		this.arList = new ArrayList<AbstractData>();
		this.adList = new ArrayList<AbstractData>();
		this.observers = new ArrayList<ProjectObserver>();
		file = target;

		SAXBuilder sxb = new SAXBuilder();
	    Document document;
	   	document = sxb.build(target);
		Element root = document.getRootElement();
			     
		name = root.getAttributeValue(nameAtt);
		if (name == null) {
			logger.warn("Missing  " + nameAtt + " attribute of project in project file.");
			name = "";	
		}
		Element child = root.getChild(modelCn);
		if (child != null)
			for(Element m:child.getChildren()){
				if (m.getName() == modelElt)
					addModel(m);
			}
		else
			logger.warn("Missing <" + modelCn + "> markup in project file.");
		child = root.getChild(reqCn);
		if (child != null)
			for(Element c:child.getChildren()){
				if (c.getName() == reqElt)
					addRequirement(c);			    	 
			}
		else
			logger.warn("Missing <" + reqCn + "> markup in project file.");
		child = root.getChild(resCn);
		if(child != null)
			for(Element c:child.getChildren()){
				if (c.getName() == resElt)
					addData(c);
			}
		else
			logger.warn("Missing <" + resCn + "> markup in project file.");
	    empty = false;
	    saved = true;
	}

	private void addRequirement(Element c) {
		String id = c.getAttributeValue(typeAtt);
		if (id == null) {
			logger.warn("Missing " + typeAtt + " attribute of requirement in project file.");
			id = "";
		}
		AbstractData req = null;
		for(AbstractDataFactory arf:Controler.getControler().getADFList()){
			if (arf.getId().equals(id) && arf instanceof AbstractRequirementFactory){
				try{
					String name = c.getAttributeValue(nameAtt);
					if (name == null) {
						logger.warn("Missing " + nameAtt + " attribute of requirement in project file.");
						name = "";
					}
					String content = c.getChildText(contc);
					String reqfile = c.getChildText(filec);
					if (content != null) {
						req = arf.createAbstractData(name, content);
					}
					else if (reqfile != null) {
						File f = new File(reqfile);
						req = arf.createAbstractData(name, f);
					}
					else {
						logger.warn("Missing <" + contc + "> or <" + filec + "> markup of requirement in project file.");
						req = arf.createAbstractData(name, "");
					}
					addRequirement(req);
				} catch(PlasmaDataException e){
				    logger.error(e.getMessage());
					JOptionPane.showMessageDialog(pControler.getOwner(),
							"An error occured while loading requirement\n"
							+ e.getMessage()
							, "Project creation error", JOptionPane.ERROR_MESSAGE);
				}
				return;
			}
		}
		// if not found, should still create the model with some blank type
		req = addText(c,id);
		if (req != null)
			addRequirement(req);
	}

	private void addModel(Element m) {
		String id = m.getAttributeValue(typeAtt);
		if (id == null) {
			logger.warn("Missing " + typeAtt + " attribute of model in project file.");
			id = "";
		}
		AbstractData model = null;
		for(AbstractDataFactory amf:Controler.getControler().getADFList()){
			if (amf.getId().equals(id) && amf instanceof AbstractModelFactory){
				try{
					String name = m.getAttributeValue(nameAtt);
					if (name == null) {
						logger.warn("Missing " + nameAtt + " attribute of model in project file.");
						name = "";
					}
					String content = m.getChildText(contc);
					String modelfile = m.getChildText(filec);
					if (content != null) {
						model = amf.createAbstractData(name, content);
					}
					else if (modelfile != null) {
						File f = new File(modelfile);
						model = amf.createAbstractData(name, f);
					}
					else {
						logger.warn("Missing <" + contc + "> or <" + filec + "> markup of model in project file.");
						model = amf.createAbstractData(name, "");
					}
					addModel(model);
				} catch(PlasmaDataException e){
				      logger.error(e.getMessage());
					JOptionPane.showMessageDialog(pControler.getOwner(),
							"An error occured while loading requirement\n"
							+ e.getMessage()
							, "Project creation error", JOptionPane.ERROR_MESSAGE);
				}
				return;
			}
		}
		// if not found, should still create the model with some blank type
		model = addText(m,id);
		if (model != null)
			addModel(model);
	}
	
	private void addData(Element c) {
		String id = c.getAttributeValue(typeAtt);
		if (id == null) {
			logger.warn("Missing " + typeAtt + " attribute of resource in project file.");
			id = "";
		}
		AbstractData data = null;
		for(AbstractDataFactory adf:Controler.getControler().getADFList()){
			if (adf.getId().equals(id) && adf instanceof AbstractDataFactory){
				try{
					String name = c.getAttributeValue(nameAtt);
					if (name == null) {
						logger.warn("Missing " + nameAtt + " attribute of resource in project file.");
						name = "";
					}
					String content = c.getChildText(contc);
					String datafile = c.getChildText(filec);
					if (content != null) {
						data = adf.createAbstractData(name, content);
					}
					else if (datafile != null) {
						File f = new File(datafile);
						data = adf.createAbstractData(name, f);
					}
					else {
						logger.warn("Missing <" + contc + "> or <" + filec + "> markup of resource in project file.");
						data = adf.createAbstractData(name, "");
					}
					addAbstractData(data);
				} catch(PlasmaDataException e){
				    logger.error(e.getMessage());
					JOptionPane.showMessageDialog(pControler.getOwner(),
							"An error occured while loading requirement\n"
							+ e.getMessage()
							, "Project creation error", JOptionPane.ERROR_MESSAGE);
				}
				return;
			}
		}
		// if not found, should still create the data with some blank type
		data = addText(c,id);
		if (data != null)
			addAbstractData(data);
	}
	
	private AbstractData addText(Element c, String id) {
		logger.info("Plugin not found: "+id);
		logger.info("Searching txt factory.");
		for(AbstractDataFactory txtF:Controler.getControler().getADFList()){
			if (txtF.getId().equals("text") && txtF instanceof TextDataFactory){
				String name = c.getAttributeValue(nameAtt);
				if (name == null) {
					logger.warn("Missing " + nameAtt + " attribute of text in project file.");
					name = "";
				}
				String content = c.getChildText(contc);
				String datafile = c.getChildText(filec);
				AbstractData data = null;
				try {
					if (content != null) {
						data = ((TextDataFactory)txtF).
								createTextData(name, content, new PlasmaPluginNotFound(id), id);
					}
					else if (datafile != null) {
						File f = new File(datafile);
						data = ((TextDataFactory)txtF).
								createTextData(name, f, new PlasmaPluginNotFound(id), id);
					}
					else {
						logger.warn("Missing <" + contc + "> or <" + filec + "> markup of text in project file.");
						data = ((TextDataFactory)txtF).
								createTextData(name, "", new PlasmaPluginNotFound(id), id);
					}
				}
				catch(PlasmaDataException e){
				    logger.error(e.getMessage());
					JOptionPane.showMessageDialog(pControler.getOwner(),
							"An error occured while loading text data\n"
							+ e.getMessage()
							, "Project creation error", JOptionPane.ERROR_MESSAGE);
				}
				logger.info("Data instantiated with the plain text type.");
				return data;
			}
		}
		logger.warn("Text plugin not found, data not instantiated.");
		logger.warn("Saving this project could cause data loss.");
		return null;
	}

	/**
	 * Save the project to its File(s):
	 * - if the models, requirements or data have been created from the xml content save the content in the xml
	 * - if the models, requirements or data have been created from files save the file path and save content in the files 
	 * 
	 */
	public void save(){
		Element root = new Element("PlasmaProject");
		Document doc = new Document(root);
		root.setAttribute(nameAtt, name);
		/*e = new Element(typeAtt);
		e.addContent(type);
		root.addContent(e);*/
		Element e = new Element(modelCn);
		root.addContent(e);
		for(AbstractData m:amList){
			Element dataElt = new Element("model");
			dataElt.setAttribute(nameAtt, m.getName());
			if (m.getOrigin() == null) {
				Element cont = new Element(contc);
				cont.setText(m.getContent());
				dataElt.addContent(cont);
			}
			else {
				Element fileElt = new Element(filec);
				fileElt.setText(m.getOrigin().getPath());
				dataElt.addContent(fileElt);
				try {
					FileWriter writer = new FileWriter(m.getOrigin().getAbsoluteFile());
					writer.write(m.getContent());
					writer.close();
				}
				catch (IOException exc) {
					logger.error(exc.getMessage(), exc);
				}
			}
			dataElt.setAttribute(typeAtt, m.getId());
			e.addContent(dataElt);
		}
		e = new Element(reqCn);
		root.addContent(e);
		for(AbstractData r:arList){
			Element dataElt = new Element("requirement");
			dataElt.setAttribute(nameAtt, r.getName());
			if (r.getOrigin() == null) {
				Element cont = new Element(contc);
				cont.setText(r.getContent());
				dataElt.addContent(cont);
			}
			else {
				Element fileElt = new Element(filec);
				fileElt.setText(r.getOrigin().getPath());
				dataElt.addContent(fileElt);
				try {
					FileWriter writer = new FileWriter(r.getOrigin().getAbsoluteFile());
					writer.write(r.getContent());
					writer.close();
				}
				catch (IOException exc) {
					logger.error(exc.getMessage(), exc);
				}
			}
			dataElt.setAttribute(typeAtt, r.getId());
			e.addContent(dataElt);
		}
		e = new Element(resCn);
		root.addContent(e);
		for(AbstractData r:adList){
			Element dataElt = new Element("resource");
			dataElt.setAttribute(nameAtt, r.getName());
			if (r.getOrigin() == null) {
				Element cont = new Element(contc);
				cont.setText(r.getContent());
				dataElt.addContent(cont);
			}
			else {
				Element fileElt = new Element(filec);
				fileElt.setText(r.getOrigin().getPath());
				dataElt.addContent(fileElt);
				try {
					FileWriter writer = new FileWriter(r.getOrigin().getAbsoluteFile());
					writer.write(r.getContent());
					writer.close();
				}
				catch (IOException exc) {
					logger.error(exc.getMessage(), exc);
				}
			}
			dataElt.setAttribute(typeAtt, r.getId());
			e.addContent(dataElt);
		}
	    try {
	      XMLOutputter serializer = new XMLOutputter(Format.getPrettyFormat());
	      FileWriter writer = new FileWriter(file);
	      serializer.output(doc, writer);
	      //serializer.output(doc, System.out);
          writer.flush();
	      writer.close();
	    }
	    catch (Exception exc) {
	      logger.error(exc.getMessage(), exc);
	    }
	    empty = false;
	}
	
	/**
	 * Return true if all Data of this project are empty and the project isn't saved on the hard drive.
	 * @return
	 */
	public boolean isEmpty(){
		return empty;
	}
	@Override
	public String toString(){
		return name;
	}
	
	public void rename(String newName){
		String oldName = name;
		name = newName;
		for(ProjectObserver obs:observers){
			obs.renamed(oldName, this);
		}
	}
	
	/************************************ MODEL & REQUIREMENT ******************************************************/
	public void addModel(AbstractData am){
		amList.add(am);
		for(ProjectObserver obs:observers){
			obs.addData(am, this);
		}
    	empty = false;
	}
	
	public void addRequirement(AbstractData ar){
		arList.add(ar);
		for(ProjectObserver obs:observers){
			obs.addData(ar, this);
		}
    	empty = false;
	}

	public void addAbstractData(AbstractData ad) {
		adList.add(ad);
		for(ProjectObserver obs:observers){
			obs.addData(ad, this);
		}
    	empty = false;
	}
	
	public void remData(AbstractData data){
		arList.remove(data);
		amList.remove(data);
		adList.remove(data);
		for(ProjectObserver obs:observers){
			obs.remData(data, this);
		}
	}
	
	public void sendUpdateDataSignal(AbstractData data){
		for(ProjectObserver obs:observers)
			obs.updateData(data, this);
	}
	
	/**
	 * 
	 * @return true if at least one of its data has error
	 */
	public boolean hasError(){
		for(AbstractData am : amList)
			if(am.getErrors().size()>0)
				return true;
		for(AbstractData ar : arList)
			if(ar.getErrors().size()>0)
				return true;
		return false;
	}
	
	/************************************ OBSERVER *****************************************************************/
	public void addObserver(ProjectObserver obs){
		observers.add(obs);
	}
	
	public void remObserver(ProjectObserver obs){
		observers.remove(obs);
	}	
	
	public void close(){
		for(ProjectObserver obs:observers){
			obs.remProject(this);
		}
	}
	public void register() {
		for(ProjectObserver obs:observers){
			obs.addProject(this);
		}
	}
	public void openData(Object leaf) {
		if(amList.contains(leaf) || arList.contains(leaf) || adList.contains(leaf)){
			for(ProjectObserver obs:observers){
				obs.openData((AbstractData)leaf, this);
			}
		}
	}

	
	/************************************ GETTERS & SETTERS***********************************************************/
	public String getName(){
		return name;
	}
	public List<AbstractData> getAMList() {
		return amList;
	}
	public List<AbstractData> getARList() {
		return arList;
	}
	public List<AbstractData> getADList() {
		return adList;
	}
	public File getFile(){
		return file;
	}
	public void setFile(File file){
		this.file = file;
	}
	public ProjectControler getpControler() {
		return pControler;
	}

	public boolean isSaved() {
		return saved;
	}
	
	public void setSaved(boolean saved){
		this.saved = saved;
	}
}
