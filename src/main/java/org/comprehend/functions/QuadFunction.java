package org.comprehend.functions;

public interface QuadFunction<P1, P2, P3, P4, S> {
    S apply(P1 p1, P2 p2, P3 p3, P4 p4);
}
