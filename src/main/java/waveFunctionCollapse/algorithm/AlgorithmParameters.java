package waveFunctionCollapse.algorithm;

import waveFunctionCollapse.tilesets.TileSet;

import java.awt.*;

public record AlgorithmParameters(Dimension dimension, int tileSize, int tilesHorizontal, int tilesVertical, int algorithmSpeed, StartConfiguration startConfiguration, float nonRandomFactor) {

    public AlgorithmParameters(final Dimension dimension, final int tileSize, final int algorithmSpeed, final StartConfiguration startConfiguration, final float nonRandomFactor) {
        this(dimension, tileSize, dimension.width / tileSize, dimension.height / tileSize, algorithmSpeed, startConfiguration, nonRandomFactor);
    }

    public static class Builder {
        private TileSet tileSet;
        private Dimension dimension;
        private int tileSize, algorithmSpeed;
        private StartConfiguration startConfiguration;
        private float nonRandomFactor;

        public Builder() {
        }

        public Builder dimension(final Dimension dimension) {
            this.dimension = dimension;
            return this;
        }

        public Builder tileSize(final int tileSize) {
            this.tileSize = tileSize;
            return this;
        }

        public Builder algorithmSpeed(final int algorithmSpeed) {
            this.algorithmSpeed = algorithmSpeed;
            return this;
        }

        public Builder startConfiguration(final StartConfiguration startConfiguration) {
            this.startConfiguration = startConfiguration;
            return this;
        }

        public Builder nonRandomFactor(final float nonRandomFactor) {
            this.nonRandomFactor = nonRandomFactor;
            return this;
        }

        public AlgorithmParameters build() {
            if (dimension == null || tileSize == 0) {
                throw new RuntimeException("Not all necessary parameters were specified in the builder.");
            }
            return new AlgorithmParameters(dimension, tileSize, algorithmSpeed, startConfiguration, nonRandomFactor);
        }
    }
}
