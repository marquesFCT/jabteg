
package br.usp.each.saeg.agdtpoo.util;

import br.usp.each.saeg.agdtpoo.entity.PrimitiveType;
import br.usp.each.saeg.agdtpoo.entity.TestIndividual;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashSet;

public class Features {
         
    public static PrimitiveType getPrimitiveType(String typeName)
    {
        // Padronizar as entradas
        typeName = typeName.toLowerCase();
        
        // Remover o indexador de arrays
        typeName = typeName.replace("[]", "");
        
        // Apagar espacos desnecessarios
        typeName = typeName.trim();
        
        if (typeName.equals("boolean"))
            return PrimitiveType.Boolean;
        else if (typeName.equals("int"))
            return PrimitiveType.Integer;
        else if (typeName.equals("string"))
            return PrimitiveType.String;    
        else if (typeName.equals("long"))
            return PrimitiveType.Long;    
        else if (typeName.equals("double"))
            return PrimitiveType.Double;      
        else if (typeName.equals("byte"))
            return PrimitiveType.Byte;  
        else if (typeName.equals("char"))
            return PrimitiveType.Character;    
        else if (typeName.equals("float"))
            return PrimitiveType.Float;     
        else if (typeName.equals("short"))
            return PrimitiveType.Short;    
        else if (typeName.equals("void"))
            return PrimitiveType.Void;   
        else 
            return PrimitiveType.None;        
    }
    
    public static String getInstanceName(TestIndividual individual)
    {        
        String instanceName;
        
        instanceName = individual.getBytecodeType().getTypeName().toLowerCase();
        
        if (instanceName.contains("."))
            instanceName = instanceName.substring(instanceName.lastIndexOf(".")+1);
        
        return "$" + instanceName;
    }
    
    public static boolean isAbstract(Type type)   
    {
        boolean returnValue = false;
        
        if (isPrimitive(type))
            return false;
        
        Class currentClass = getClassFromType(type);
        
        returnValue = Modifier.isAbstract(currentClass.getModifiers());
        
        return returnValue;
    }
    
    private static Class getClassFromType(Type type)
    {
        Class currentClass = (Class)type;
        
        return currentClass;
    }
    
    public static boolean isPrimitive(Type type, Class instance)
    {
        boolean returnValue = false;
        
        returnValue = isPrimitive(type);
                
        return returnValue;
    }
   
    public static Class getArrayComponentType(Type type)
    {
        Class currentClass = getClassFromType(type);
        
        Class componentType = currentClass.getComponentType();
        
        return componentType;
    }
    
    public static boolean isArray(Type type)
    {
        Class currentClass = getClassFromType(type);
        
        boolean returnValue = currentClass.isArray();
        
        return returnValue;
    }
    
    public static boolean isString(Type type)
    {
        boolean returnValue = false;
        
        if (getPrimitiveType(type) == PrimitiveType.String)
            returnValue = true;
        
        return returnValue;
    }
    
    public static boolean isPrimitive(Type type)
    {
        boolean returnValue = false;        
        PrimitiveType priType;
        
        // Consulta a função de validação de tipos primitivos
        priType = getPrimitiveType(type);
                    
        // Verifica se o tipo retornado não é um NONE,
        // que significa que o tipo de dados não se encaicha 
        // com nenhum dos tipos primitivos previstos
        if (priType != PrimitiveType.None)
            returnValue = true;
        
        return returnValue;
    }
    
    // Associa uma enumeração de tipo PrimitiveTypes a um tipo de dados
    // ps.: neste caso Strings também são associadas como tipos primitivos
    public static PrimitiveType getPrimitiveType(Type type)
    {
        PrimitiveType returnValue;
        
         if (Boolean.TYPE == type)
             returnValue = PrimitiveType.Boolean;
         else if (Character.TYPE == type)
             returnValue = PrimitiveType.Character;
         else if (Byte.TYPE == type)
             returnValue = PrimitiveType.Byte;
         else if (Short.TYPE == type) 
             returnValue = PrimitiveType.Short;
         else if (Integer.TYPE == type)
             returnValue = PrimitiveType.Integer;
         else if (Long.TYPE == type) 
             returnValue = PrimitiveType.Long;
         else if (Float.TYPE == type)
             returnValue = PrimitiveType.Float;
         else if (Double.TYPE == type)
             returnValue = PrimitiveType.Double;
         else if (Void.TYPE == type)
             returnValue = PrimitiveType.Void;
        // O tipo string nativamente não é um tipo primitivo, 
        // mas para o funcionamento desta proposta será definido como um,
        // pois se for encarado como um tipo primitivo, sua utilização será 
        // mais fácil dentro deste contexto proposto.
         else if (getTypeName(type).compareTo("java.lang.String") == 0)
             returnValue = PrimitiveType.String;
         else
             returnValue = PrimitiveType.None;
        
        return returnValue;
    }
    
    public static boolean isPrimitive(Class instance)
    {
        return WRAPPER_TYPES.contains(instance);
    }
    
    private static final HashSet<Class> WRAPPER_TYPES = getWrapperTypes();
    
    private static HashSet<Class> getWrapperTypes()
    {
        HashSet<Class> ret = new HashSet<Class>();
        
        ret.add(Boolean.class);
        ret.add(Character.class);
        ret.add(Byte.class);
        ret.add(Short.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(Void.class);     
        ret.add(String.class);   
        ret.add(int.class); 
        
        return ret;
    }
    
    public static String getTypeName(Type target)
    {
        String typeName = target.toString();
                
        typeName = typeName.replace("class ", "");
        
        return typeName;
    }
    
    public static String getMethodSignature(String signature)
    {        
        int first = signature.indexOf("(");
        String aux = signature.substring(0, first - 1);
        first = aux.lastIndexOf(" ");
        aux = aux.substring(0, first).trim();
        signature = signature.replace(aux, "").trim();
              
        first = signature.indexOf("(");
        aux = signature.substring(first);
        aux = aux.replace(")", "");
        aux = aux.replace("(", "");
        aux = aux.replace(", ", ",");

        String[] parametros = aux.split(",");        
        
        if (!parametros[0].trim().equals(""))
        {
            for (int i = 0; i < parametros.length; i++) {
                if (parametros[i].contains(" "))
                    parametros[i] = parametros[i].substring(0, parametros[i].lastIndexOf(" "));
                
                if (parametros[i].contains("."))
                    parametros[i] = parametros[i].substring(parametros[i].lastIndexOf(".") + 1);
            }

            if (signature.contains("("))
                signature = signature.substring(0, signature.indexOf("(") + 1);

            if (signature.contains("."))
                signature = signature.substring(signature.indexOf(".") + 1);
            
            for (int i = 0; i < parametros.length; i++) {
                signature += parametros[i] + ", ";
            }
            
            signature = signature.trim();
            
            if (signature.endsWith(","))
                signature = signature.substring(0, signature.length() - 1);
            
            signature += ")";
        }
        
        signature = signature.replace(" throws", "");
        
        if (signature.contains(".") && (signature.indexOf(".") < signature.indexOf("(")))
            signature = signature.substring(signature.lastIndexOf(".") + 1);
        
        return signature;
    }
}
