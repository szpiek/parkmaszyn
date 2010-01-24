package Utilities;

import java.util.ArrayList;
import java.util.Collection;

import EntityBeans.Emploee;
import EntityBeans.Machine;
import EntityBeans.Rezerwation;
import flex.messaging.io.amf.ASObject;
import flex.messaging.io.amf.translator.ASTranslator;

public class FlexToJavaConverter {
	
	@SuppressWarnings("unchecked")
	public static  ArrayList<Machine> convertMchineArray(ArrayList array){
        ArrayList<Machine> machineArray = new ArrayList<Machine>();
        ASTranslator ast = new ASTranslator();
        Machine machine;
        ASObject aso;
        
        for (int i=0;i< array.size(); i++){
            machine = new Machine();
            if(!(array.get(i) instanceof Machine) )
            {
	            aso = new ASObject();
	
	            aso = (ASObject) array.get(i);
	            aso.setType("EntityBeans.Machine");
            machine = (Machine) ast.convert(aso, Machine.class);
            } 
            machine=(Machine) array.get(i);
            machineArray.add(machine);
        }
        return machineArray;
    }
	
	@SuppressWarnings("unchecked")
	public static  ArrayList<Emploee> convertEmploeeArray(ArrayList array){
        ArrayList<Emploee> emploeeArray = new ArrayList<Emploee>();
        ASTranslator ast = new ASTranslator();
        Emploee emplorr;
        ASObject aso;

        for (int i=0;i< array.size(); i++){
            emplorr = new Emploee();
            aso = new ASObject();

            aso = (ASObject) array.get(i);
            aso.setType("EntityBeans.Emploee");
            emplorr = (Emploee) ast.convert(aso, Emploee.class);
            emploeeArray.add(emplorr);
        }
        return emploeeArray;
    }
	
	@SuppressWarnings("unchecked")
	public static  ArrayList<Rezerwation> convertRezerwationArray(ArrayList array){
        ArrayList<Rezerwation> rezerwationArray = new ArrayList<Rezerwation>();
        ASTranslator ast = new ASTranslator();
        Rezerwation rezerwation;
        ASObject aso;

        for (int i=0;i< array.size(); i++){
            rezerwation = new Rezerwation();
            if(!(array.get(i) instanceof Rezerwation) )
            {
            aso = new ASObject();

            aso = (ASObject) array.get(i);
            aso.setType("EntityBeans.Rezerwation");
            rezerwation = (Rezerwation) ast.convert(aso, Rezerwation.class);
            } else rezerwation=(Rezerwation) array.get(i);
            rezerwationArray.add(rezerwation);
        }
        return rezerwationArray;
    }
	
	@SuppressWarnings("unchecked")
	public static ArrayList convertFromPersistentBag(Collection c)
	{
		ArrayList ret=new ArrayList();
		if(c!=null)ret.addAll(c);
		return ret;
	}
}
