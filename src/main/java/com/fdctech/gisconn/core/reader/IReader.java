package com.fdctech.gisconn.core.reader;

import org.apache.logging.log4j.Logger;

import java.io.File;

import static java.lang.String.format;

public interface IReader<T> {
    Logger getLogger();

    T readMessage(File file) throws Exception;

    void initFolders();

    default void moveToFolder(File file, String destinationFolder) {
        File directoryToMove = new File(destinationFolder);
        if (!directoryToMove.exists())
            if (directoryToMove.mkdirs())
                getLogger().info(format("Create path %s", destinationFolder));
            else {
                getLogger().error(format("Failed to created necessary path %s", destinationFolder));
                return;
            }
        File fileToReplace = new File(String.format("%s\\%s", directoryToMove.getAbsolutePath(), file.getName()));
        if (file.renameTo(fileToReplace))
            getLogger().info(format("Template file %s moved to folder %s", file.getName(), destinationFolder));
        else
            getLogger().error(format("Can't move file %s to folder %s", file.getName(), destinationFolder));
    }

    default void initFolder(File folder) {
        if (!folder.exists())
            if (folder.mkdirs())
                getLogger().info(String.format("%s path created", folder.getAbsolutePath()));
            else
                getLogger().error(String.format("%s path don't exist and can't be created.", folder.getAbsolutePath()));

    }
}
