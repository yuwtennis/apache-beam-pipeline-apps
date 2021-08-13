package net.yuwtennis.app;

import net.yuwtennis.app.entities.PipelineDomainEntity;
import org.apache.beam.sdk.Pipeline;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) {
        System.out.println( "Hello World!" );

        PipelineDomainEntity entity = new PipelineDomainEntity();

        try {
            // Use reflection for dynamic class loading.
            // https://www.oracle.com/technical-resources/articles/java/javareflection.html

            // Creating New Objects.
            Class cls = Class.forName(args[1]);
            Constructor ct = cls.getConstructor();
            Object instance = ct.newInstance();

            // Invoking Methods by Name.
            Class partypes[] = new Class[1];
            partypes[0] = Pipeline.class ;

            Method meth = cls.getMethod("run", partypes[0]);
            meth.invoke(instance, entity.create());

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
