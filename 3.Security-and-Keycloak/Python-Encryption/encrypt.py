# pip install cryptography
# pip install pycryptodome

from Crypto.Cipher import AES
from Crypto.Util.Padding import pad
import base64
from Crypto.Util.Padding import unpad


################# Encrypt #################
def encrypt(message, key, iv):
    cipher = AES.new(key, AES.MODE_CBC, iv=iv)
    ct_bytes = cipher.encrypt(pad(message.encode(), AES.block_size))
    iv_b64 = base64.b64encode(iv).decode('utf-8')
    ct_b64 = base64.b64encode(ct_bytes).decode('utf-8')
    return iv_b64, ct_b64

key = b'1234567890abcdef'  # 16-byte key
iv = b'1234567890abcdef'  # 16-byte iv

message = "RUHPI0801S"

iv, ciphertext = encrypt(message, key, iv)

print(f"Initialization Vector (IV): {iv}")
print(f"Ciphertext: {ciphertext}")


