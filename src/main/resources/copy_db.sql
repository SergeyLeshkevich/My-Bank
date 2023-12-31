PGDMP          5                {            postgres    15.2    15.2 ,    )           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            *           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            +           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            ,           1262    5    postgres    DATABASE     |   CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Russian_Russia.1251';
    DROP DATABASE postgres;
                postgres    false            -           0    0    DATABASE postgres    COMMENT     N   COMMENT ON DATABASE postgres IS 'default administrative connection database';
                   postgres    false    3372                        3079    16384 	   adminpack 	   EXTENSION     A   CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;
    DROP EXTENSION adminpack;
                   false            .           0    0    EXTENSION adminpack    COMMENT     M   COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';
                        false    2            �            1259    16427    accounts    TABLE     �   CREATE TABLE public.accounts (
    id integer NOT NULL,
    number character varying(255) NOT NULL,
    id_user integer,
    id_bank integer NOT NULL,
    balance double precision DEFAULT 0,
    date timestamp without time zone
);
    DROP TABLE public.accounts;
       public         heap    postgres    false            �            1259    16426    accounts_id_seq    SEQUENCE     �   CREATE SEQUENCE public.accounts_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.accounts_id_seq;
       public          postgres    false    218            /           0    0    accounts_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.accounts_id_seq OWNED BY public.accounts.id;
          public          postgres    false    217            �            1259    16459    banks    TABLE     a   CREATE TABLE public.banks (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);
    DROP TABLE public.banks;
       public         heap    postgres    false            �            1259    16458    banks_id_seq    SEQUENCE     �   CREATE SEQUENCE public.banks_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.banks_id_seq;
       public          postgres    false    223            0           0    0    banks_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.banks_id_seq OWNED BY public.banks.id;
          public          postgres    false    222            �            1259    16453 	   passwords    TABLE     o   CREATE TABLE public.passwords (
    id_user integer NOT NULL,
    password character varying(1000) NOT NULL
);
    DROP TABLE public.passwords;
       public         heap    postgres    false            �            1259    16443    transactions    TABLE     �  CREATE TABLE public.transactions (
    id integer NOT NULL,
    date timestamp without time zone,
    number_sender_account character varying(255) NOT NULL,
    number_beneficiary_account character varying(255) NOT NULL,
    type_operation character varying(255) NOT NULL,
    amount double precision DEFAULT 0,
    id_sender_bank integer NOT NULL,
    id_beneficiary_bank integer NOT NULL,
    status character varying(255) NOT NULL
);
     DROP TABLE public.transactions;
       public         heap    postgres    false            �            1259    16442    transactions_id_seq    SEQUENCE     �   CREATE SEQUENCE public.transactions_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.transactions_id_seq;
       public          postgres    false    220            1           0    0    transactions_id_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.transactions_id_seq OWNED BY public.transactions.id;
          public          postgres    false    219            �            1259    16402    users    TABLE     �   CREATE TABLE public.users (
    id integer NOT NULL,
    login character varying(255) NOT NULL,
    lastname character varying(255) NOT NULL,
    firstname character varying(255) NOT NULL
);
    DROP TABLE public.users;
       public         heap    postgres    false            �            1259    16401    users_id_seq    SEQUENCE     �   CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.users_id_seq;
       public          postgres    false    216            2           0    0    users_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;
          public          postgres    false    215            z           2604    16430    accounts id    DEFAULT     j   ALTER TABLE ONLY public.accounts ALTER COLUMN id SET DEFAULT nextval('public.accounts_id_seq'::regclass);
 :   ALTER TABLE public.accounts ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    218    217    218            ~           2604    16462    banks id    DEFAULT     d   ALTER TABLE ONLY public.banks ALTER COLUMN id SET DEFAULT nextval('public.banks_id_seq'::regclass);
 7   ALTER TABLE public.banks ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    222    223    223            |           2604    16446    transactions id    DEFAULT     r   ALTER TABLE ONLY public.transactions ALTER COLUMN id SET DEFAULT nextval('public.transactions_id_seq'::regclass);
 >   ALTER TABLE public.transactions ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    219    220    220            y           2604    16405    users id    DEFAULT     d   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    215    216    216            !          0    16427    accounts 
   TABLE DATA           O   COPY public.accounts (id, number, id_user, id_bank, balance, date) FROM stdin;
    public          postgres    false    218   �-       &          0    16459    banks 
   TABLE DATA           )   COPY public.banks (id, name) FROM stdin;
    public          postgres    false    223   �0       $          0    16453 	   passwords 
   TABLE DATA           6   COPY public.passwords (id_user, password) FROM stdin;
    public          postgres    false    221   ;1       #          0    16443    transactions 
   TABLE DATA           �   COPY public.transactions (id, date, number_sender_account, number_beneficiary_account, type_operation, amount, id_sender_bank, id_beneficiary_bank, status) FROM stdin;
    public          postgres    false    220   �4                 0    16402    users 
   TABLE DATA           ?   COPY public.users (id, login, lastname, firstname) FROM stdin;
    public          postgres    false    216   �5       3           0    0    accounts_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.accounts_id_seq', 208, true);
          public          postgres    false    217            4           0    0    banks_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.banks_id_seq', 6, true);
          public          postgres    false    222            5           0    0    transactions_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.transactions_id_seq', 3528, true);
          public          postgres    false    219            6           0    0    users_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.users_id_seq', 38, true);
          public          postgres    false    215            �           2606    16433    accounts accounts_pk 
   CONSTRAINT     R   ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT accounts_pk PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.accounts DROP CONSTRAINT accounts_pk;
       public            postgres    false    218            �           2606    16464    banks banks_pk 
   CONSTRAINT     L   ALTER TABLE ONLY public.banks
    ADD CONSTRAINT banks_pk PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.banks DROP CONSTRAINT banks_pk;
       public            postgres    false    223            �           2606    16466    banks banks_pk2 
   CONSTRAINT     H   ALTER TABLE ONLY public.banks
    ADD CONSTRAINT banks_pk2 UNIQUE (id);
 9   ALTER TABLE ONLY public.banks DROP CONSTRAINT banks_pk2;
       public            postgres    false    223            �           2606    16468    banks banks_pk3 
   CONSTRAINT     J   ALTER TABLE ONLY public.banks
    ADD CONSTRAINT banks_pk3 UNIQUE (name);
 9   ALTER TABLE ONLY public.banks DROP CONSTRAINT banks_pk3;
       public            postgres    false    223            �           2606    16451    transactions transactions_pk 
   CONSTRAINT     Z   ALTER TABLE ONLY public.transactions
    ADD CONSTRAINT transactions_pk PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.transactions DROP CONSTRAINT transactions_pk;
       public            postgres    false    220            �           2606    16409    users users_pk 
   CONSTRAINT     L   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pk PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pk;
       public            postgres    false    216            �           1259    16434    accounts_id_uindex    INDEX     L   CREATE UNIQUE INDEX accounts_id_uindex ON public.accounts USING btree (id);
 &   DROP INDEX public.accounts_id_uindex;
       public            postgres    false    218            �           1259    16435    accounts_number_uindex    INDEX     T   CREATE UNIQUE INDEX accounts_number_uindex ON public.accounts USING btree (number);
 *   DROP INDEX public.accounts_number_uindex;
       public            postgres    false    218            �           1259    16452    transactions_id_uindex    INDEX     T   CREATE UNIQUE INDEX transactions_id_uindex ON public.transactions USING btree (id);
 *   DROP INDEX public.transactions_id_uindex;
       public            postgres    false    220                       1259    16410    users_id_uindex    INDEX     F   CREATE UNIQUE INDEX users_id_uindex ON public.users USING btree (id);
 #   DROP INDEX public.users_id_uindex;
       public            postgres    false    216            �           1259    16412    users_login_uindex    INDEX     L   CREATE UNIQUE INDEX users_login_uindex ON public.users USING btree (login);
 &   DROP INDEX public.users_login_uindex;
       public            postgres    false    216            !   �  x�]Uɑ"A{V�L�]�-�+U1K��@���2��[yF�[�͛
^M�o���K�rY6_ڣyT�M�?���o��dy�4`�QQ���+o5b՗���(��TuC�%��W��5͊9���	�K��0
r��ďnj
�\��	àv�� ڗ�s0�KcY��<&T�����n%�ڌ�ֶc�ͯ����K�����Ț�q�K�`u�.�K�~����7�:�
$^:�ٱV��o,���s� ��N�7ؠ\+r	'�4���8ل����d�v8Fί���
�ա@I�e��PPoC�s���Yۥݓ���9X���[;ޡ	��'�m��WWh@��V{�0(A��Q�܁}�]��?hM����e��j�K{��
�9��o������qߵA���F	�H'�W��X�݉�t�ic�#!+�m�4��\��j�4�h��%�k��P��
�s����b«�U��,q�j�=�Q@�����1�Hx,����ns�r�����0X�ݱ�'�	2 ŁA��B�|����ӯ4AkR,.���g�NÀ���+i��0�g�O'��=�&;��ߏM~�Hn	�Ϛ��QZ���8��N�]���x`�m��	Z�l��w~"�a���ds�ƅ��_qc���k\��`�E��u�
�F���p�݄�=��$�.��dtE���9K4������_��	+RR�Ln�-���"�O'�����������9#      &   ?   x�3�t�I-K-�uJ���2�t�IK���9�RK�lN��\(۔3*�(�6�I-.��c���� �"M      $   �  x�=�ǲ�J����u�qݒ�$�J���9�-W�hϟ�V�$��&?$�1_��]V�A�Bm�:J��v��]���:B]z�;p��/�`$��6�����$��ǽŋ2��n�c���m)��F�u��\ca����ُ�a�.E��d�G��[��tl,���|!��m޳d� �{�
�b$�����.�e`���w�r(��|W�t��%XT��N�%e�c):���~}d�o��l����f|��a���HkEm����X�	��~��rOj��z�Ϗ��m|�ﶭiW��4����u�D}I�A��F��t�(��$K!th^�h��:j>�NR#�bj����q�Y�A���Ԏ��nn`�=�Y����j��\b�1��s�w�^��!m��?}|ou��|���]n���us�b9�;'3�hr~�"��CU:By�O}����]9�dX\u�QMn�7�(��hg��1y�����������ZSD�
����>��`$-�l3:e�Z����с�������`��p��r�up.�Ș�u�����k�`�Q�٦�[k��&Q�����.�E^� ��'�z�Zq�Ͼ�Y��wu��	�����෿��/l�ЧA��%��p�Y���'s�i?1uB�g*q��=������~��qb��:ď�ͽ�e=����We�Sc��P���qǾ��C��b����6�"L�EN�У����221A���t��z?��$(���|у�G��K�E�Y��|��G&9�N��\S�n�e��*	S�@}�[ʽ?�3E}SeS�S�/�k��ԅT�HM䘎��*7_1��X��h8���FuO�z�e hM���� �Қ��8'�u�ﮇ�o�#�%�jw��<��:)�a^����=�3W�������d}Gc��G���0��^��      #   �   x�]�;�0E��yl �|<C�. ���&�J$��E(����Q�B��tGԩw�#�pV�yrK*�����kZ��cZ��vs0c9�C-Ǡ&��:���Č�S�aM�f�c?�.}=]4L���c���#:         <  x�5��n1E���To��)��P7l��"B��h���$��G����9D8n��`�������]���,��aJ�P�s����t7�����K�x'>�Xp��1�d,w�~�'v��O��K�x��p
U+�X�5!�R<�=�|�	���:|�:��U��#\�l;�`M��FU�e �����$�şa_ml-,۴^�%������ﳤ���a�x:c��&��X�`�G��,�X�:��
����,�X�r;w�A��"�**o�ѳ�I�F�U��G��E�g]Ŏ��xf[�Dfa��؋����b���υ�     