Frits Dannenberg, University of Oxford, Balliol college, Department of Computer Science - 2014-04-27
Frits.Dannenberg@cs.ox.ac.uk

DISCLAIMER

THIS SOFTWARE IS PROVIDED BY ME, THE AUTHOR, "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.




This PRISM benchmark uses DNA circuits described in:

F. Dannenberg, M. Kwiatkowska, C. Thachuk and A. J. Turberfield.
DNA Walker Circuits: Computational Potential, Design, and Verification.
In Proc. 19th International Conference on DNA Computing and Molecular Programming (DNA 19). 2013. 
http://www.veriware.org/bibitem.php?key=DKTT13


For PRISM support for fast adaptive uniformisation please see

F. Dannenberg, E. M. Hahn and M. Kwiatkowska.
Computing Cumulative Rewards using Fast Adaptive Uniformisation.
In Proc. 11th Conference on Computational Methods in Systems Biology (CMSB'13). 2013.
http://www.prismmodelchecker.org/bibitem.php?key=DHK13

CONTENTS:

	benchControl.sh		Generates results for the 8-anchorage control circuit using standard uniformisation. (Fig 9). Use GUI PRISM with control_fix.pctl to create plots (using K=0:2:200).
	benchTrack12.sh		Generates results for a 12-anchorage single-junction circuit using fast adaptive uniformisation. (Fig 10a)
	benchTrack28Double.sh	Generates results for a 28-anchorage double-junction circuit with double blockades using simulation. (Fig 10c)

For more info see /model and /layout of the tracks & descriptions therein.

To cite this benchmark please cite ``DNA Walker Circuits: Computational Potential, Design, and Verification.'' 
To cite the FAU implementation in PRISM please cite ``Computing Cumulative Rewards using Fast Adaptive Uniformisation''


*** INSTALLATION & RUNNING THE BENCHMARK ***

1) Install prism if not yet installed. Prism 4.1 or higher is required.
2) Change "YOUR_PRISM_PATH_HERE" to your prism path in benchControl.sh, benchTrack12.sh and benchTrack28Double.sh
3) run 

./benchControl.sh		-- 8 anchorage control circuit using standard uniformisation and standard options (hybrid engine)
./benchTrack12.sh		-- 12 anchorage single-junction circuit using fast adaptive uniformisation
./benchTrack28Double.sh		-- 28 anchorage double-junction circuit using simulation

./bench.sh 			-- all of the above


