/**
 * This file is part of fr.inria.plasmalab.root.
 *
 * fr.inria.plasmalab.root is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.root is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.root.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import org.jdom2.Element;

import fr.inria.plasmalab.workflow.data.AbstractData;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.factory.AbstractRequirementFactory;

@PluginImplementation
public class TestRequirementFactory implements AbstractRequirementFactory{
	private static final String name = "Test Requirement", description = "Test requirement with deterministic results.";
	private static final String id = "testRequirement";
	private static final String nameAtt = "name", contc="content";

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getId() {
		return id;
	}
	
	public String toString(){
		return name;
	}

	@Override
	public AbstractData fromJDOM(Element node) {
		String name = node.getAttributeValue(nameAtt);
		String content = node.getChildText(contc);
		return new TestRequirement(name, content);
	}

	@Override
	public AbstractRequirement createAbstractRequirement(String name) {
		return new TestRequirement(name, "");
	}

	@Override
	public AbstractRequirement createAbstractRequirement(String name, File file) {
		String content = "";
		try {
			FileReader fr = new FileReader(file);;
			BufferedReader br = new BufferedReader(fr);
			while(br.ready())
				content = content+br.readLine()+"\n";
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new TestRequirement(name, content);
	}

	@Override
	public AbstractRequirement createAbstractRequirement(String name, String content) {
		return new TestRequirement(name, content);
	}

	@Override
	public AbstractData createAbstractData(String name){
		return createAbstractRequirement(name);
	}
	@Override
	public AbstractData createAbstractData(String name, File file){
		return createAbstractRequirement(name, file);
	}
	@Override
	public AbstractData createAbstractData(String name, String content){
		return createAbstractRequirement(name, content);
	}

	@Override
	public void exportTo(AbstractData data, File file) {
		try {
			FileWriter fw = new FileWriter(file);
			String content = data.getContent();
			fw.write(content);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
