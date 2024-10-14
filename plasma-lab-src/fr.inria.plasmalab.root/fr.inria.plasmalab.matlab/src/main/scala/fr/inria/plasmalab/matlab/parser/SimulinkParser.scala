/**
 * This file is part of fr.inria.plasmalab.matlab.
 *
 * fr.inria.plasmalab.matlab is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.matlab is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.matlab.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.matlab.parser

import scala.util.parsing.combinator.JavaTokenParsers
import scala.collection.mutable.ListBuffer
import fr.inria.plasmalab.matlab.components.SimulinkOptimVariable
import fr.inria.plasmalab.matlab.components.SimulinkVariableConstraint

object SimulinkParser extends JavaTokenParsers{
	var modelPath: String = ""
	var modelFile: String = ""
    var optimList: ListBuffer[SimulinkOptimVariable] = ListBuffer()
    var constraintList: ListBuffer[SimulinkVariableConstraint] = ListBuffer()

    def getOptimAsJavaList = {
	  var list = new java.util.LinkedList[SimulinkOptimVariable]()
	  for(optim <- optimList)
	    list.add(optim)
	  list  
	}
    
	def getConstraintAsJavaList = {
	  var list = new java.util.LinkedList[SimulinkVariableConstraint]()
	  for(cons <- constraintList)
	    list.add(cons)
	  list  
	}
	
    def _simulink_ = _modelPath_ ~ _optims_.? ~_constraint_.*
    
    def _modelPath_ = _path_ ~ ("." ~> ident ).? ^^ {
	  case file_path ~ ext=> {
	    var path = ""
	    var fileExt = ""
	    path = file_path._1
        path = path.substring(0, path.size-(file_path._2.size+1))
        fileExt += file_path._2
	    
        ext match {
          case Some(s) => fileExt += "." + s
          case None => null
        }
	    modelPath = path
	    modelFile = path+fileExt
	  }
	}
	
    def _path_ = ("/").? ~ repsep((_ident_ | ".." | "." ), "/") ^^ {
      case pre_path ~ list_path=> {
   	    var path = ""
   	    path += pre_path.getOrElse("")
        for (i <- list_path)
          path += i + "/"
          
        if(list_path.size>0)
          Tuple2(path, list_path.last)
        else 
          Tuple2(path, "")
      }
    }
    
    def _ident_ = ident ~ (("-") ~ ident).* ^^ {
      case first ~ list_other => {
        var id = ""
        id += first
        for (i <- list_other){
          for(j <- i._1)
            id += j
          id += i._2
        }
        id
      }
    }
    
    def _optims_ = "/".? ~> _optim_.*
    
    def _optim_ =  (_path_ <~ "=") ~ _dValue_ ~ _interval_ ^^ {
      case ident ~ dValue ~ interval=> {
        optimList += new SimulinkOptimVariable(ident._1, dValue, interval._1,interval._2,interval._3)
      }
    }
    
    def _dValue_ = decimalNumber <~ "=>" ^^ (_.toInt)
    
    def _interval_ = 
        ("[" ~>(decimalNumber ^^ (_.toInt)) ~ 
        (";" ~> decimalNumber ^^ (_.toInt)) ~ 
        (";" ~> decimalNumber ^^ (_.toInt)).? <~ "]") ^^ {
      case min ~ max ~ inc => 
        new Tuple3(min,max,inc.getOrElse(1))
    }
    
   def _constraint_ = _path_ ~ _compOp_ ~ _path_ ^^ {
     case ident1 ~ op ~ ident2=> {
    	 constraintList  += new SimulinkVariableConstraint(ident1._1, ident2._1, op)
     }
   }
   
   def _compOp_ = ("<" | "<=") ^^ {
     _ match {
       	case "<" => true
       	case "<=" => false
       	case _ => false
     }
   }

    
  /**
   * Entry point
   */
  def parseSimulinkPlasmaModel(model: String) = {
    println("parsing "+model)
    optimList.clear
    modelPath = ""
    parseAll(_simulink_, model)
    
    println("path= "+modelPath)
    for(optim <- optimList)
      println(optim.getName() + " ="+optim.defaultValue+"=> " + optim.getMin()+","+optim.getMax()+","+optim.getInc())
    true
  }
    
  /**
  * Main method to run test
  */
  def main(args: Array[String]) {
    val model = """
    			|/home/kcorre/workspace/git/PLASMA_Lab/plasma-lab/plasma_lab_examples/simulink/sldemo_fuelsys_estasys.mdl
    			|sldemo_fuelsys_estasys/Nominal_Speed =300=> [200;500;100]
    			|sldemo_fuelsys_estasys/Nominal_Speed2 =300=> [200;500;100]
    			""".stripMargin  
    			
    parseAll(_simulink_, model)
    
    println(modelPath)
    for(optim <- optimList)
      println(optim.getName() + " ="+optim.defaultValue+"=> " + optim.getMin()+","+optim.getMax()+","+optim.getInc())
  }
}