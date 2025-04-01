package voidjam.occ.skills.weaponinnate;

import com.google.common.collect.Lists;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import voidjam.occ.gameassets.OCCAnimations;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public class DarkSlayer extends WeaponInnateSkill {

   public DarkSlayer(WeaponInnateSkill.Builder<?> builder) {
      super(builder);
   }
   
   public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
      executer.playAnimationSynchronized(OCCAnimations.NO, 0.0F);
    }

	public List<Component> getTooltipOnItem(ItemStack itemStack, CapabilityItem cap, PlayerPatch<?> playerCap) {
		List<Component> list = Lists.newArrayList();
		String traslatableText = this.getTranslationKey();
		list.add(Component.translatable(traslatableText).withStyle(ChatFormatting.WHITE).append(Component.literal(String.format("[%.0f]", this.consumption)).withStyle(ChatFormatting.AQUA)));
		list.add(Component.translatable(traslatableText + ".tooltip").withStyle(ChatFormatting.DARK_GRAY));
		return list;
	}

   public boolean isExecutableState(PlayerPatch<?> executer) {
      executer.updateEntityState();
      EntityState playerState = executer.getEntityState();
      return !((Player)executer.getOriginal()).isFallFlying() && executer.currentLivingMotion != LivingMotions.FALL && playerState.canUseSkill() && executer.getEntityState().canBasicAttack();
   }

   public boolean resourcePredicate(PlayerPatch<?> playerpatch) {
      return true;
   }

   public void updateContainer(SkillContainer container) {
      super.updateContainer(container);
      container.setResource(0.1F);
   }
}
