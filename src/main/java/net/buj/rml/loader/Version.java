package net.buj.rml.loader;

import net.buj.rml.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

public class Version {
    public final int[] version;
    public final String tag;
    public final int tagVersion;

    public Version(int[] version) {
        this.version = version;
        tag = null;
        tagVersion = -1;
    }
    public Version(int[] version, String tag, int tagVersion) {
        this.version = version;
        this.tag = tag;
        this.tagVersion = tagVersion;
    }

    public static Version from(String str) {
        if (!str.matches("^\\d+(\\.\\d+)*(-[a-z]+(\\.\\d+)?)?$")) {
            throw new IllegalArgumentException("String is not a valid version");
        }

        String tag = null;
        int tagVersion = -1;

        int tagIndex = str.indexOf("-") + 1;
        if (tagIndex != 0) {
            int versionIndex = str.lastIndexOf(".") + 1;
            if (versionIndex > tagIndex) {
                tagVersion = Integer.parseInt(str.substring(versionIndex));
                versionIndex--;
            }
            else versionIndex = str.length();
            tag = str.substring(tagIndex, versionIndex);
            tagIndex--;
        }
        else tagIndex = str.length();
        String[] version$1 = str.substring(0, tagIndex).split("\\.");
        int[] version = new int[version$1.length];

        for (int i = 0; i < version$1.length; i++) {
            version[i] = Integer.parseInt(version$1[i]);
        }

        return new Version(version, tag, tagVersion);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < version.length; i++) {
            builder.append(version[i]);
            if (version.length - 1 != i) builder.append(".");
        }
        if (tag != null) {
            builder.append("-");
            builder.append(tag);
            if (tagVersion > -1) {
                builder.append(".");
                builder.append(tagVersion);
            }
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Version)) return false;
        Version version1 = (Version) o;
        return tagVersion == version1.tagVersion && Arrays.equals(version, version1.version) && Objects.equals(tag, version1.tag);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(tag, tagVersion);
        result = 31 * result + Arrays.hashCode(version);
        return result;
    }
}
