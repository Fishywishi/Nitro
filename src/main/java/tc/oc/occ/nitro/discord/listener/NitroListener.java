package tc.oc.occ.nitro.discord.listener;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import tc.oc.occ.nitro.NitroConfig;
import tc.oc.occ.nitro.discord.DiscordBot;

import java.util.List;

public abstract class NitroListener extends ListenerAdapter {

  protected final NitroConfig config;
  protected final DiscordBot api;

  public NitroListener(DiscordBot api, NitroConfig config) {
    this.config = config;
    this.api = api;
  }

  protected boolean isNitro(Member user) {
    if (api.getServer() != null) {
      List<Role> roles = user.getRoles();
      return roles.stream().anyMatch(this::isNitro);
    }
    return false;
  }

  protected boolean isNitro(Role role) {
    return role.getId().equalsIgnoreCase(config.getNitroRole());
  }

  protected boolean isNitroBanned(Member member) {
      if (api.getServer() != null) {
          List<Role> roles = member.getRoles();
          return roles.stream().anyMatch(this::isNitroBanned);
      }
      return false;
  }

  protected boolean isNitroBanned(Role role) {
      return role.getId().equalsIgnoreCase(config.getNitroBanRole());
  }

}
