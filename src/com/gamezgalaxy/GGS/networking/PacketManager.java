package com.gamezgalaxy.GGS.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import com.gamezgalaxy.GGS.networking.packets.*;
import com.gamezgalaxy.GGS.server.Player;
import com.gamezgalaxy.GGS.server.Server;

public class PacketManager {
	
	protected Packet[] packets = new Packet[] {
		new Connect(this),
		new DespawnPlayer(this),
		new FinishLevelSend(this),
		new GlobalPosUpdate(this),
		new Kick(this),
		new LevelSend(this),
		new LevelStartSend(this),
		new Message(this),
		new Ping(this),
		new PosUpdate(this),
		new ServerMessage(this),
		new SetBlock(this),
		new SpawnPlayer(this),
		new TP(this),
		new UpdateUser(this),
		new Welcome(this)
	};
	
	protected ServerSocket serverSocket;
	
	protected Thread reader;
	
	public Server server;
	
	public PacketManager(Server instance) {
		this.server = instance;
		try {
			serverSocket = new ServerSocket(this.server.Port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Packet getPacket(byte opCode) {
		for (Packet p : packets) {
			if (p.ID == opCode)
				return p;
		}
		return null;
	}
	
	public Packet getPacket(String name) {
		for (Packet p : packets) {
			if (p.name.equalsIgnoreCase(name))
				return p;
		}
		return null;
	}
	
	public void StartReading() {
		reader = new Read(this);
		reader.start();
		server.Log("Listening on port " + server.Port);
	}
	
	public static long ConvertToInt32(byte[] array) {
		long toreturn = 0;
		for (int i = 0; i < array.length; i++) {
			toreturn += ((long) array[i] & 0xffL) << (8 * i);
		}
		return toreturn;
	}
	public static short INT_little_endian_TO_big_endian(short i)
	{
		return(short)(((i&0xff)<<24)+((i&0xff00)<<8)+((i&0xff0000)>>8)+((i>>24)&0xff));
	}
	/**
     * Encodes an integer into up to 4 bytes in network byte order in the 
     * supplied buffer starting at <code>start</code> offset and writing
     * <code>count</code> bytes.
     * 
     * @param num the int to convert to a byte array
     * @param buf the buffer to write the bytes to
     * @param start the offset from beginning for the write operation
     * @param count the number of reserved bytes for the write operation
     */
    public static void intToNetworkByteOrder(int num, byte[] buf, int start, int count) {
        if (count > 4) {
            throw new IllegalArgumentException(
                    "Cannot handle more than 4 bytes");
        }

        for (int i = count - 1; i >= 0; i--) {
            buf[start + i] = (byte) (num & 0xff);
            num >>>= 8;
        }
    }
	
	public class Read extends Thread {
		
		PacketManager pm;
		
		public Read(PacketManager pm) { this.pm = pm; }
		
		@Override
		public void run() {
			Socket connection = null;
			while (server.Running) {
				try {
					connection = serverSocket.accept();
					server.Log("Connection made from " + connection.getInetAddress().toString());
					new Player(connection, pm);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
