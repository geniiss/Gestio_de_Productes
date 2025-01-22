package tests;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
* La clase Runner es un ejecutor de pruebas personalizado para ejecutar pruebas JUnit desde la línea de comandos.
* Toma uno o más nombres de clase completamente calificados como argumentos y ejecuta las pruebas en esas clases.
*/
public class Runner {
    /**
    * Método principal para ejecutar pruebas JUnit para las clases especificadas.
    *
    * @param args Matriz de nombres de clases que se probarán. Cada clase debe ser un nombre completo.
    */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Por favor, pasa al menos una clase de test.");
            return;
        }

        for (String className : args) {
            try {
                Class<?> testClass = Class.forName(className);
                Result result = JUnitCore.runClasses(testClass);

                System.out.println("Resultados para " + testClass.getName() + ":");

                // Recopilem noms de tests fallats
                Set<String> failedTests = new HashSet<>();
                for (Failure failure : result.getFailures()) {
                    String failedTestName = failure.getDescription().getMethodName();
                    failedTests.add(failedTestName);
                    System.out.println(failedTestName + " - FAIL: " + failure.getMessage());
                }

                // Identifiquem i comprovem tots els mètodes anotats com @Test
                for (Method method : testClass.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(Test.class)) {
                        String methodName = method.getName();
                        if (failedTests.contains(methodName)) {
                            // Ja hem imprès el resultat del test fallat
                            continue;
                        }
                        System.out.println(methodName + " - OK");
                    }
                }

                // Missatge general del resultat
                if (result.wasSuccessful()) {
                    System.out.println("Tots els tests han passat correctament.");
                } else {
                    System.out.println("Hi ha fallades en els tests.");
                }
            } catch (ClassNotFoundException e) {
                System.out.println("Clase no trobada: " + className);
            }
        }
    }
}
