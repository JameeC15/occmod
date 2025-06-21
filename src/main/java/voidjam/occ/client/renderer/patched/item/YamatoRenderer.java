package voidjam.occ.client.renderer.patched.item;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import voidjam.occ.world.items.OCCItems;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@OnlyIn(Dist.CLIENT)
public class YamatoRenderer extends RenderItemBase {
   private final ItemStack yamatoBlade;

   public YamatoRenderer() {
      this.yamatoBlade = new ItemStack((ItemLike)OCCItems.YAMATO_BLADE.get());
   }

   public void renderItemInHand(ItemStack stack, LivingEntityPatch<?> entitypatch, InteractionHand hand, HumanoidArmature armature, OpenMatrix4f[] poses, MultiBufferSource buffer, PoseStack poseStack, int packedLight, float partialTicks) {
      OpenMatrix4f modelMatrix = new OpenMatrix4f(this.mainhandcorrectionMatrix);
      modelMatrix.mulFront(poses[armature.toolL.getId()]);
      poseStack.pushPose();
      this.mulPoseStack(poseStack, modelMatrix);
      Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, (Level)null, 0);
      poseStack.popPose();
      
      ItemStack blade = ((LivingEntity)entitypatch.getOriginal()).getMainHandItem();
      if (blade.hasTag() && (blade.getTag().getInt("CustomModelData") == 2)) {
         modelMatrix = new OpenMatrix4f(this.mainhandcorrectionMatrix);
         modelMatrix.mulFront(poses[armature.toolR.getId()]);
         poseStack.pushPose();
         this.mulPoseStack(poseStack, modelMatrix);
         Minecraft.getInstance().getItemRenderer().renderStatic(this.yamatoBlade, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, (Level)null, 0);
         poseStack.popPose();

      } else if (blade.hasTag() && (blade.getTag().getInt("CustomModelData") == 4)) {
         modelMatrix = new OpenMatrix4f(this.mainhandcorrectionMatrix);
         modelMatrix.mulFront(poses[armature.toolR.getId()]);
         poseStack.pushPose();
         this.mulPoseStack(poseStack, modelMatrix);
         Minecraft.getInstance().getItemRenderer().renderStatic(this.yamatoBlade, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, (Level)null, 0);
         poseStack.popPose();
      }

   }
}

