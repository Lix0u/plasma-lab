package fr.inria.plasmalab.csvsimulator;

import java.io.File;

import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.factory.AbstractModelFactory;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class CSVFactory extends AbstractModelFactory {
	private static final String name = "CSV";
	private static final String description = "Simulates traces from csv files";
	private static final String id = "csv";
	
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

	@Override
	public AbstractModel createAbstractModel(String name) throws PlasmaDataException {
		return new CSVSimulator(name, "", id);
	}

	@Override
	public AbstractModel createAbstractModel(String name, File file) throws PlasmaDataException {
		return new CSVSimulator(name, file, id);
	}

	@Override
	public AbstractModel createAbstractModel(String name, String content) throws PlasmaDataException {
		return new CSVSimulator(name, content, id);
	}

}
