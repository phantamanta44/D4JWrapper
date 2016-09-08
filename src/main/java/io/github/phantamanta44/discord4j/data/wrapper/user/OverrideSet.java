package io.github.phantamanta44.discord4j.data.wrapper.user;

import io.github.phantamanta44.discord4j.data.Permission;

import java.util.Collection;
import java.util.stream.Stream;

public class OverrideSet {

    private final Collection<Permission> allow, deny;

    public OverrideSet(Collection<Permission> allow, Collection<Permission> deny) {
        this.allow = allow;
        this.deny = deny;
    }

    public Stream<Permission> allow() {
        return allow.stream();
    }

    public Stream<Permission> deny() {
        return deny.stream();
    }

}
