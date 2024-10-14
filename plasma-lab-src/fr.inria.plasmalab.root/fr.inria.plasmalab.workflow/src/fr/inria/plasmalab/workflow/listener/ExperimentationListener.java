/**
 * This file is part of fr.inria.plasmalab.workflow.
 *
 * fr.inria.plasmalab.workflow is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.workflow is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.workflow.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.workflow.listener;


import java.util.List;

import fr.inria.plasmalab.workflow.shared.ResultInterface;

public interface ExperimentationListener {
	public void notifyAlgorithmStarted(String nodeURI);
	public void notifyAlgorithmCompleted(String nodeURI);
	public void notifyAlgorithmStopped(String nodeURI);
	public void notifyAlgorithmError(String nodeURI, String errorMessage);
	public void notifyNewServiceConnected(String nodeURI);
	public void notifyServiceDisconnected(String nodeURI);
	public void publishResults(String nodeURI, List<ResultInterface> results);
	public void notifyProgress(int percent);
	public void notifyTimeRemaining(long milliseconds);
}
