package me.nobeld.minecraft.noblelib.dragon;

public enum DragonPhase {
        DEAD, DYING, PHASE2, PHASE1;
        public boolean isLive() {
                return this == PHASE1 || this == PHASE2;
        }
}