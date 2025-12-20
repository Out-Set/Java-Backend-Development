from Crypto.Cipher import AES
from Crypto.Util.Padding import pad
import base64
from Crypto.Util.Padding import unpad


################# Decrypt #################
def decrypt(iv, ciphertext, key):
    # Fix the padding issue
    while len(ciphertext) % 4 != 0:
        ciphertext += '='

    ciphertext = base64.b64decode(ciphertext)
    cipher = AES.new(key, AES.MODE_CBC, iv)
    pt = unpad(cipher.decrypt(ciphertext), AES.block_size)
    return pt.decode('utf-8')

key = b'1234567890abcdef'  # 16-byte key
iv = b'1234567890abcdef'  # 16-byte iv

ciphertext = "tvDdOVdDfUo1OmEtE8+Ayw==" #mGzaxdBCEJOgZHNafsFmVQ==
print("Decrypted Message :: ", decrypt(iv, ciphertext, key))


# ciphertext_list = [
  
# ]

# decrypted_list = [decrypt(iv, ct, key) for ct in ciphertext_list]
# print(decrypted_list)
# for pan in decrypted_list:
#     print(f"'{pan}'")