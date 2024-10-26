package waveFunctionCollapse.algorithm;

import java.awt.*;

public record AlgorithmParameters(Dimension dimension, int tileSize, int algorithmSpeed, StartConfiguration startConfiguration, float nonRandomFactor) {

    public int tilesHorizontal() {
        return dimension.width / tileSize;
    }

    public int tilesVertical() {
        return dimension.height / tileSize;
    }



    public static class Builder {
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
