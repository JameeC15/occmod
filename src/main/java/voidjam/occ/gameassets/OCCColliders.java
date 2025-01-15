package voidjam.occ.gameassets;

import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.MultiOBBCollider;
import yesman.epicfight.api.collider.OBBCollider;

public class OCCColliders {
   public static final Collider YAMATO = new MultiOBBCollider(3, 0.4, 0.4, 1.0, 0.0, 0.0, -0.5);
   public static final Collider NO_HITBOX = new OBBCollider(0.1D, 0.1D, 0.1D, 0D, 0.0D, 0.0D);
   public static final Collider YAMATO_SHEATH = new MultiOBBCollider(3, 0.5, 0.5, 1.0, 0.0, 0.0, 0.5);
   public static final Collider YAMATO_P = new MultiOBBCollider(3, 0.4, 0.4, 1.5, 0.0, 0.0, -0.5);
   public static final Collider JUDGEMENT_CUT = new OBBCollider(1.7, 1.0, 4, 0.0, 1.0, -3);
   public static final Collider YAMATO_DASH = new OBBCollider(1.7, 1.0, 2.0, 0.0, 1.0, -1.0);
   public static final Collider YAMATO_P0 = new OBBCollider(1.7, 1.0, -3.5, 0.0, 1.0, -2.5);
   public static final Collider DS_DIMENSION = new OBBCollider(5.5D, 2.0D, 5.5D, 0.0D, 2.3D, 0D);
   public static final Collider RISING_STAR = new OBBCollider(1.5D, 1.0D, 1.5D, 0.0D, 1.3D, 0D);
   public static final Collider AERIAL_CLEAVE = new OBBCollider(0.6D, 2.5D, 1.5D, 0.0D, 1.25D, -0.75D);
   public static final Collider DS_DIMENSION_A = new OBBCollider(5.5D, 2.0D, 5.5D, 0.0D, -1D, -0.5D);
   public static final Collider DROPKICK = new OBBCollider(4.5D, 2.0D, 4.5D, 0.0D, 2.0D, -0.5D);
   public static final Collider YAMATO_DASH_FINISH = new OBBCollider(1.7, 1.0, 3.5, 0.0, 1.0, 1.0);

   public OCCColliders() {
   }
}
