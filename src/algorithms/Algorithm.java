package algorithms;

import path.Path;

public abstract class Algorithm {
    private Path path;

    public Algorithm(Path path) {
        this.path = path;
        this.solve();
    }

    public abstract void solve();

    public Path getPath() {
        return this.path;
    }
}
