# AES_Encryption_Decryption
Implementing Advanced Encryption Standard (AES) to Encrypt and Decrypt text.

> AES has specifications:
>
>> Data should be 128-bit long
>>
>> Keyspace can be one of the following lengths: {128-bit - 192-bit - 256-bit}. Note that the keyspace used in this implementaion is 128-bit.
>

* (No. Rounds) or (NO. of subkeys generated) depends on the keyspace. So if:

* The keyspace = 128-bit, NO. Rounds = 10, No. subkeys = 11

* The keyspace = 128-bit, NO. Rounds = 12, No. subkeys = 13

* The keyspace = 128-bit, NO. Rounds = 14, No. subkeys = 15

> There are Three stages:
>
>> Key Generation
>>
>> Encryption
>>
>> Decrypton
>

## Key Generation

> this key ofcourse will be used in the both cases {Encryption & Decryption}. So user enters his desired key as a String of ASCII characters. Then from here the Key Generation starts.
>
> Steps to perform Key Generation stage:
>
>> 1- Turn this string into hex and transform its shape to be a matrix (state) and this will be subkey1.
>>
>> 2- Get the last column in the matrix of previous key and rotate it.
>> 
>> 3- perfrom subByte (SBOX) on that column.
>> 
>> 4- XOR the output with first column of the previous key and XOR the output with the corresponding RCON.
>> 
>> 5- Now you have the first column of the new subkey. XOR it with the second column in the previous key.
>> 
>> 6- Now you have the second column of the new subkey. XOR it with the third column in the previous key.
>> 
>> 7- Now you have the third column of the new subkey. XOR it with the fourth column in the previous key.
>> 
>> 8- Now you have the fourth column of the new subkey. Integerate these four columns to generate the full new subkey.
>> 
>> 9- Repeat starting from step 2 till you get total 11 subkeys.
>

## Encryption
![image](https://user-images.githubusercontent.com/68112462/204407927-91bd2c49-8bc0-4223-a065-517c72b81f98.png)

* Note that there are initial key addition layer before the first round and there is no MixColumn layer in the last round.

## Decryption

* To perform Decryption you only need to reverse the sequence of Encryption steps.
