/**
 * This file is part of fr.inria.plasmalab.terminal.
 *
 * fr.inria.plasmalab.terminal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.terminal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.terminal.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package fr.inria.plasmalab.terminal.outputlistener;

import java.io.File;
import java.util.List;

import fr.inria.plasmalab.terminal.outputlistener.api.OutputListener;
import fr.inria.plasmalab.terminal.outputlistener.api.impl.CsvOutputListener;
import fr.inria.plasmalab.terminal.outputlistener.api.impl.ProbaOutputListener;
import fr.inria.plasmalab.terminal.outputlistener.api.impl.TextOutputListener;
import fr.inria.plasmalab.terminal.parameter.OutputFormat;

/**
 * 
 * Factory for output listener.
 * 
 * @author msimonin
 *
 */
public final class OutputListenerFactory
{

    /**
     * 
     * create a new output listener.
     * 
     * @param output    The output to use
     * @param outputFormat The output format to use
     * @param progress 
     * @return a new Output Listener
     */
    public static OutputListener createOutputListener(File output, OutputFormat outputFormat, boolean progress, List<String> headers) {
        OutputListener outputListener = null;
        
        switch (outputFormat) {
        case text:
            outputListener = new TextOutputListener(output, progress, headers);
            break;
        case csv:
            outputListener = new CsvOutputListener(output, progress, headers);
            break;
        case proba:
            outputListener = new ProbaOutputListener(output, progress, headers);
            break;
        default:
            outputListener = new TextOutputListener(output, progress, headers);
        }
        return outputListener;
    }

}
