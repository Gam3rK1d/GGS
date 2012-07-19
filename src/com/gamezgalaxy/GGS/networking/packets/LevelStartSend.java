package com.gamezgalaxy.GGS.networking.packets;

import java.io.IOException;

import com.gamezgalaxy.GGS.networking.Packet;
import com.gamezgalaxy.GGS.networking.PacketManager;
import com.gamezgalaxy.GGS.networking.PacketType;
import com.gamezgalaxy.GGS.server.Player;
import com.gamezgalaxy.GGS.server.Server;

public class LevelStartSend extends Packet {

	public LevelStartSend(String name, byte ID, PacketManager parent,
			PacketType packetType) {
		super(name, ID, parent, packetType);
		// TODO Auto-generated constructor stub
	}
	
	public LevelStartSend(PacketManager pm) {
		super("Start Level Send", (byte)0x02, pm, PacketType.Server_to_Client);
	}

	@Override
	public void Write(Player player, Server server) {
		try {
			player.WriteData(new byte[] { ID });
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void Handle(byte[] message, Server server, Player player) {
		// TODO Auto-generated method stub
		
	}

}
