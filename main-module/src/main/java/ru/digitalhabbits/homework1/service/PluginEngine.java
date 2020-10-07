package ru.digitalhabbits.homework1.service;

import ru.digitalhabbits.homework1.plugin.PluginInterface;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PluginEngine {

    @Nonnull
    public  <T extends PluginInterface> String applyPlugin(@Nonnull Class<T> cls, @Nonnull String text) {
        // TODO: NotImplemented -> done
        String result = "";

        try {
            //найдем метод
            Method methodApply = cls.getMethod("apply", String.class);
            //создадим новый объект
            PluginInterface pluginInterface = cls.newInstance();
            //вызовем метод на новом объекте
            result = (String) methodApply.invoke(pluginInterface, text);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return result;
    }
}
