package ru.digitalhabbits.homework1.service;

import static org.slf4j.LoggerFactory.getLogger;
import org.slf4j.Logger;
import ru.digitalhabbits.homework1.plugin.PluginInterface;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

public class PluginLoader {
    private static final Logger logger = getLogger(PluginLoader.class);

    private static final String PLUGIN_EXT = "jar";
    private static final String PACKAGE_TO_SCAN = "ru.digitalhabbits.homework1.plugin";

    @Nonnull
    public List<Class<? extends PluginInterface>> loadPlugins(@Nonnull String pluginDirName) {
        // TODO: NotImplemented -> done

        List<Class<? extends PluginInterface>> plugins=new ArrayList<>();

        //Директория для просмотра файлов
        File pluginDirectory=new File(pluginDirName);
        File[] files=pluginDirectory.listFiles((dir, name) -> name.endsWith(PLUGIN_EXT));

        //Загрузка плагинов
        if(files!=null && files.length>0) {
            getPluginsClasses(files).forEach(className->{
                try {
                    //Получим свой загрузчик файлов и загрузим с помощью него плагины
                    Class cls=getClassLoaderByFilesURL(files)
                            .loadClass(className
                                    .replaceAll("/",".")
                                    .replace(".class",""));

                    //Если класс расширяет PluginInterface, то добавим этот класс в массив
                    Class[] interfaces=cls.getInterfaces();
                    for(Class intface:interfaces) {
                        if(intface.equals(PluginInterface.class)) {
                            plugins.add(cls);
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            });
        }

        return plugins;
    }

    private ArrayList<String> getPluginsClasses (File[] files) {
        ArrayList<String> classes=new ArrayList<>();

        //Переберем файлы в jar архиве и добавим в массив те, что расширяют .class
        for(File file:files) {
            try {
                JarFile jar=new JarFile(file);
                jar.stream().forEach(jarEntry -> {
                    if(jarEntry.getName().endsWith(".class")) {
                        classes.add(jarEntry.getName());
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return classes;
    }

    private URLClassLoader getClassLoaderByFilesURL (File[] files) {
        ArrayList<URL> urls=new ArrayList<>(files.length);

        //Получаем ссылки (URL) на плагины
        for(File file:files) {
            try {
                URL url=file.toURI().toURL();
                urls.add(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        //Создаем загрузчик файлов по ссылкам
        return new URLClassLoader(urls.toArray(new URL[urls.size()]));
    }
}
