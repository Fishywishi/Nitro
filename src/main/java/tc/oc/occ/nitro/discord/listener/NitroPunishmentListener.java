package tc.oc.occ.nitro.discord.listener;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;
import tc.oc.occ.nitro.NitroCloudy;
import tc.oc.occ.nitro.NitroConfig;
import tc.oc.occ.nitro.data.NitroUser;
import tc.oc.occ.nitro.discord.DiscordBot;
import tc.oc.occ.nitro.events.NitroUserRemoveEvent;

import java.time.Duration;

public class NitroPunishmentListener extends NitroListener{
    public NitroPunishmentListener(DiscordBot api, NitroConfig config) {
        super(api, config);
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("nitro-ban")) {
            if (!event.getChannelId().equalsIgnoreCase(config.getStaffChannel())) {
                event.reply(":warning: Run this command in staff channel!").queue();
                return;
            }
            OptionMapping userOption = event.getOption("user");
            OptionMapping durationOption = event.getOption("duration");
            if (userOption == null) return;
            Member member = event.getMember();

            if (isNitroBanned(member)) {
                event.reply(":no_entry_sign: This user is already Nitro banned!").setEphemeral(true).queue();
                return;
            }
            if (config.getUsers().stream()
                    .anyMatch(user -> user.getDiscordId().equals(member.getId()))) {
                NitroUser nitro = config.getUser(member.getId()).get();
                NitroCloudy.get().callSyncEvent(new NitroUserRemoveEvent(nitro));
            }
            event.getGuild().addRoleToMember(member, event.getGuild().getRoleById(config.getNitroBanRole()));
            event.reply(":hammer: The User `" + member.getUser().getName() + "` with Discord ID `" + member.getId() + "` has been Nitro banned.").queue();
            api.alert(":hammer: " + event.getUser().getName() + "");
        }
    }

    private Duration parseDuration(String input) {
        return null;
    }
}
