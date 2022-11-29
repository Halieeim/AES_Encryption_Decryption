# AES_Encryption_Decryption
Implementing Advanced Encryption Standard (AES) to Encrypt and Decrypt text.

> AES has a specifications:
>> Data should be 128-bit long
>> Keyspace can be one of the following lengths: {128-bit - 192-bit - 256-bit}. Note that the keyspace used in this implementaion is 128-bit.

* (No. Rounds) or (NO. of subkeys generated) depends on the keyspace. So if:
** The keyspace = 128-bit, NO. Rounds = 10, No. subkeys = 11
** The keyspace = 128-bit, NO. Rounds = 12, No. subkeys = 13
** The keyspace = 128-bit, NO. Rounds = 14, No. subkeys = 15

> There are Three stages:
>> Key Generation
>> Encryption
>> Decrypton

## Key Generation
> this key ofcourse will be used in the both cases {Encryption & Decryption}. So user enters his desired key as a String of ASCII characters. Then from here the Key Generation starts.
> Steps to perform Key Generation stage:
>> 1- Turn this string into hex and transform its shape to be a matrix (state)
>> 2- 
