package voidjam.occ.client.renderer.patched.item;

import voidjam.occ.gameassets.OCCSkills;
import voidjam.occ.skills.OCCSkillDataKeys;
import voidjam.occ.world.items.OCCItems;
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
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@OnlyIn(Dist.CLIENT)
public class YamatoRenderer extends RenderItemBase {
   private final ItemStack yamatoBlade;
   private final ItemStack chair;
   private final ItemStack monster;

   public YamatoRenderer() {
      this.yamatoBlade = new ItemStack((ItemLike)OCCItems.YAMATO_BLADE.get());
      this.chair = new ItemStack((ItemLike)OCCItems.CHAIR.get());
      this.monster = new ItemStack((ItemLike)OCCItems.DRINK.get());
   }

   public void renderItemInHand(ItemStack stack, LivingEntityPatch<?> entitypatch, InteractionHand hand, HumanoidArmature armature, OpenMatrix4f[] poses, MultiBufferSource buffer, PoseStack poseStack, int packedLight, float partialTicks) {
      OpenMatrix4f modelMatrix = new OpenMatrix4f(this.mainhandcorrectionMatrix);
      modelMatrix.mulFront(poses[armature.toolL.getId()]);
      poseStack.pushPose();
      this.mulPoseStack(poseStack, modelMatrix);
      Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, (Level)null, 0);
      poseStack.popPose();

      if (entitypatch instanceof PlayerPatch && ((PlayerPatch<?>)entitypatch).getSkill(OCCSkills.DEVIL_TRIGGER) != null && ((PlayerPatch<?>)entitypatch).getSkill(OCCSkills.DEVIL_TRIGGER).getSkill().getRegistryName().getPath() == "devil_trigger" && ((PlayerPatch<?>)entitypatch).getSkill(OCCSkills.DEVIL_TRIGGER).getDataManager().getDataValue(OCCSkillDataKeys.ACTIVE.get())) {
         modelMatrix = this.getCorrectionMatrix(this.chair, entitypatch, hand);
         modelMatrix.mulFront(poses[armature.rootJoint.getId()]);
         modelMatrix.translate(0F, 0F, 0F);
         poseStack.pushPose();
         this.mulPoseStack(poseStack, modelMatrix);
         Minecraft.getInstance().getItemRenderer().renderStatic(this.chair, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, (Level)null, 0);
         poseStack.popPose();

         modelMatrix = this.getCorrectionMatrix(this.monster, entitypatch, hand);
         modelMatrix.mulFront(poses[armature.toolR.getId()]);
         modelMatrix.translate(0F, 0F, 0F);
         poseStack.pushPose();
         this.mulPoseStack(poseStack, modelMatrix);
         Minecraft.getInstance().getItemRenderer().renderStatic(this.monster, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, (Level)null, 0);
         poseStack.popPose();
      }
      
      ItemStack blade = ((LivingEntity)entitypatch.getOriginal()).getMainHandItem();
      if (blade.hasTag() && (blade.getTag().getInt("CustomModelData") == 2)) {
         modelMatrix = new OpenMatrix4f(this.mainhandcorrectionMatrix);
         modelMatrix.mulFront(poses[armature.toolR.getId()]);
         poseStack.pushPose();
         this.mulPoseStack(poseStack, modelMatrix);
         Minecraft.getInstance().getItemRenderer().renderStatic(this.yamatoBlade, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, (Level)null, 0);
         poseStack.popPose();

      }
   }
}

