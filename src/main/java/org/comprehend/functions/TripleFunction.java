package org.comprehend.functions;

public interface TripleFunction<P1,P2,P3,S> {
    S apply(P1 p1, P2 p2, P3 p3);
}
