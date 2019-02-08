package com.pagatodo.yaganaste.commons

const val RSA_KEY = "rk2QHAmXByr9wIf6d1cgU+f9NtKvj2xWvRv2wUcZSMVvhfTkcoWLG/CxEK+weoS3QcxxEWKFrWgwhYABXpkGhlXiqH7GyRIhv2kQtuZlGJJSIExd2asJrtjDnfStu7ZKbdIpLzqFUfo8naDhCuQTzhyApyJQ9HDcOSTFuRhJ7Mz3gXwUXqr98i+he+iYCzyrMViP+o4UPUqfNcpSafUw4NYre9KEZoHMaKcPMR4bMjax3Payt9LDAU3KgBOnWS9Ga6WffE03tpAWqE3ape61CmPw5QKPgRNKSnV70wu7f02jmstEepM35aSf3gL9SKMUv3DkwYIpifhNYPbdKCh+BQ=="

/* Shared Preferences */
const val SPACE = " "
const val HAS_SESSION = "HAS_SESSION"
const val SIMPLE_NAME = "SIMPLE_NAME"
const val NAME_USER = "NAME_USER"
const val FULL_NAME_USER = "FULL_NAME_USER"
const val LAST_NAME = "LAST_NAME"
const val CARD_NUMBER = "CARD_NUMBER"
const val CLABE_NUMBER = "CLABE_NUMBER"
const val ID_CUENTA = "ID_CUENTA"
const val PHONE_NUMBER = "PHONE_NUMBER"

/* CODI */
const val TAG_CODI = "TAG_CODI"
const val HAS_REGISTER_TO_RECEIVE_CODI = "HAS_REGISTER_TO_RECEIVE_CODI"
const val HAS_REGISTER_TO_SEND_CODI = "HAS_REGISTER_TO_SEND_CODI"
const val CODI_IDH = "CODI_IDH"
const val CODI_DV = "CODI_DV"
const val CODI_DV_OMISION = "CODI_DV_OMISION"
const val CODI_GOOGLE_ID = "CODI_GOOGLE_ID"
const val CODI_NOTIFICATIONS_ID = "CODI_NOTIFICATIONS_ID"
const val CODI_VERIFICATION_CODE = "CODI_VERIFICATION_CODE"
const val CODI_KEYSOURCE = "CODI_KEYSOURCE"
const val CODI_KEYSOURCE_VALIDATION_ACC = "CODI_KEYSOURCE_VALIDATION_ACC"
const val CODI_SER = "CODI_SER"
const val CODI_SER_LAST_UPDATE = "CODI_SER_LAST_UPDATE"
const val CODI_CLABE_ID = 40

/* Valores Tipos de Pago SPEI */
val SPEI_TYPES = arrayOf("19 - Presencial", "20 - No Presencial", "21 - No Presencial Recurrente", "22 - No Presencial Recurrente y No Recurrente a Nombre de Terceros")

/* JSON files */
const val BANKS_JSON = "[{\"id\":\"757\",\"banco\":\"Bancomext\"},{\"id\":\"758\",\"banco\":\"Banobras\"},{\"id\":\"759\",\"banco\":\"Banjercito\"},{\"id\":\"761\",\"banco\":\"Nafin\"},{\"id\":\"775\",\"banco\":\"Bansefi\"},{\"id\":\"776\",\"banco\":\"Hipotecaria Federal\"},{\"id\":\"779\",\"banco\":\"Banamex\"},{\"id\":\"785\",\"banco\":\"BBVA Bancomer\"},{\"id\":\"787\",\"banco\":\"Santander\"},{\"id\":\"790\",\"banco\":\"HSBC\"},{\"id\":\"794\",\"banco\":\"Bajio\"},{\"id\":\"795\",\"banco\":\"IXE\"},{\"id\":\"796\",\"banco\":\"Inbursa\"},{\"id\":\"797\",\"banco\":\"Interacciones\"},{\"id\":\"798\",\"banco\":\"Mifel\"},{\"id\":\"799\",\"banco\":\"ScotiaBank Inverlat\"},{\"id\":\"802\",\"banco\":\"BanRegio\"},{\"id\":\"803\",\"banco\":\"Invex\"},{\"id\":\"804\",\"banco\":\"Bansi\"},{\"id\":\"805\",\"banco\":\"Afirme\"},{\"id\":\"809\",\"banco\":\"Banorte/IXE\"},{\"id\":\"813\",\"banco\":\"Investa Bank\"},{\"id\":\"814\",\"banco\":\"American Express\"},{\"id\":\"816\",\"banco\":\"BANK OF AMERICA\"},{\"id\":\"818\",\"banco\":\"MUFG\"},{\"id\":\"821\",\"banco\":\"Monex\"},{\"id\":\"822\",\"banco\":\"Ve Por Mas\"},{\"id\":\"825\",\"banco\":\"ING Bank\"},{\"id\":\"831\",\"banco\":\"Credit Suisse\"},{\"id\":\"832\",\"banco\":\"Banco Azteca\"},{\"id\":\"833\",\"banco\":\"Banco Autofin\"},{\"id\":\"834\",\"banco\":\"Barclays\"},{\"id\":\"835\",\"banco\":\"Compartamos\"},{\"id\":\"836\",\"banco\":\"Famsa\"},{\"id\":\"837\",\"banco\":\"Multiva\"},{\"id\":\"838\",\"banco\":\"BM Actinver\"},{\"id\":\"840\",\"banco\":\"Intercam Banco\"},{\"id\":\"841\",\"banco\":\"BanCoppel\"},{\"id\":\"842\",\"banco\":\"ABC Capital\"},{\"id\":\"843\",\"banco\":\"UBS Bank\"},{\"id\":\"844\",\"banco\":\"ConsuBanco\"},{\"id\":\"845\",\"banco\":\"VolksWagen\"},{\"id\":\"847\",\"banco\":\"CI Banco\"},{\"id\":\"849\",\"banco\":\"BM Base\"},{\"id\":\"886\",\"banco\":\"Sabadell\"},{\"id\":\"2816\",\"banco\":\"STP\"},{\"id\":\"8609\",\"banco\":\"Ya Ganaste\"},{\"id\":\"8610\",\"banco\":\"Bankaool\"},{\"id\":\"8611\",\"banco\":\"Forjadores\"},{\"id\":\"8612\",\"banco\":\"Inmobiliario\"},{\"id\":\"8613\",\"banco\":\"Dondu00E9\"},{\"id\":\"8614\",\"banco\":\"Bancrea\"},{\"id\":\"8615\",\"banco\":\"Progreso\"},{\"id\":\"8616\",\"banco\":\"Finterra\"},{\"id\":\"8625\",\"banco\":\"ICBC\"}]"
const val COUNTIES_JSON = "[{\"ID_EntidadNacimiento\":\"1\",\"ID_Estado\":\"1\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"0\",\"Nombre\":\"AGUASCALIENTES\",\"clave\":\"AS\"},{\"ID_EntidadNacimiento\":\"2\",\"ID_Estado\":\"2\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"1\",\"Nombre\":\"BAJA CALIFORNIA\",\"clave\":\"BC\"},{\"ID_EntidadNacimiento\":\"3\",\"ID_Estado\":\"3\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"2\",\"Nombre\":\"BAJA CALIFORNIA SUR\",\"clave\":\"BS\"},{\"ID_EntidadNacimiento\":\"4\",\"ID_Estado\":\"4\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"3\",\"Nombre\":\"CAMPECHE\",\"clave\":\"CC\"},{\"ID_EntidadNacimiento\":\"6\",\"ID_Estado\":\"5\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"4\",\"Nombre\":\"CHIHUAHUA\",\"clave\":\"CH\"},{\"ID_EntidadNacimiento\":\"7\",\"ID_Estado\":\"6\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"5\",\"Nombre\":\"COAHUILA\",\"clave\":\"CL\"},{\"ID_EntidadNacimiento\":\"8\",\"ID_Estado\":\"7\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"6\",\"Nombre\":\"COLIMA\",\"clave\":\"CM\"},{\"ID_EntidadNacimiento\":\"5\",\"ID_Estado\":\"8\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"7\",\"Nombre\":\"CHIAPAS\",\"clave\":\"CS\"},{\"ID_EntidadNacimiento\":\"9\",\"ID_Estado\":\"9\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"8\",\"Nombre\":\"DISTRITO FEDERAL\",\"clave\":\"DF\"},{\"ID_EntidadNacimiento\":\"10\",\"ID_Estado\":\"10\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"9\",\"Nombre\":\"DURANGO\",\"clave\":\"DG\"},{\"ID_EntidadNacimiento\":\"12\",\"ID_Estado\":\"11\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"10\",\"Nombre\":\"GUERRERO\",\"clave\":\"GR\"},{\"ID_EntidadNacimiento\":\"11\",\"ID_Estado\":\"12\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"11\",\"Nombre\":\"GUANAJUATO\",\"clave\":\"GT\"},{\"ID_EntidadNacimiento\":\"13\",\"ID_Estado\":\"13\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"12\",\"Nombre\":\"HIDALGO\",\"clave\":\"HG\"},{\"ID_EntidadNacimiento\":\"14\",\"ID_Estado\":\"14\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"13\",\"Nombre\":\"JALISCO\",\"clave\":\"JC\"},{\"ID_EntidadNacimiento\":\"15\",\"ID_Estado\":\"15\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"14\",\"Nombre\":\"Mu00C9XICO\",\"clave\":\"MC\"},{\"ID_EntidadNacimiento\":\"16\",\"ID_Estado\":\"16\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"15\",\"Nombre\":\"MICHOACAN\",\"clave\":\"MN\"},{\"ID_EntidadNacimiento\":\"17\",\"ID_Estado\":\"17\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"16\",\"Nombre\":\"MORELOS\",\"clave\":\"MS\"},{\"ID_EntidadNacimiento\":\"19\",\"ID_Estado\":\"18\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"17\",\"Nombre\":\"NUEVO LEu00D3N\",\"clave\":\"NL\"},{\"ID_EntidadNacimiento\":\"18\",\"ID_Estado\":\"19\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"18\",\"Nombre\":\"NAYARIT\",\"clave\":\"NT\"},{\"ID_EntidadNacimiento\":\"20\",\"ID_Estado\":\"20\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"19\",\"Nombre\":\"OAXACA\",\"clave\":\"OC\"},{\"ID_EntidadNacimiento\":\"21\",\"ID_Estado\":\"21\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"20\",\"Nombre\":\"PUEBLA\",\"clave\":\"PL\"},{\"ID_EntidadNacimiento\":\"23\",\"ID_Estado\":\"22\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"21\",\"Nombre\":\"QUINTANA ROO\",\"clave\":\"QR\"},{\"ID_EntidadNacimiento\":\"22\",\"ID_Estado\":\"23\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"22\",\"Nombre\":\"QUERETARO\",\"clave\":\"QT\"},{\"ID_EntidadNacimiento\":\"25\",\"ID_Estado\":\"24\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"23\",\"Nombre\":\"SINALOA\",\"clave\":\"SL\"},{\"ID_EntidadNacimiento\":\"24\",\"ID_Estado\":\"25\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"24\",\"Nombre\":\"SAN LUIS POTOSI\",\"clave\":\"SP\"},{\"ID_EntidadNacimiento\":\"26\",\"ID_Estado\":\"26\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"25\",\"Nombre\":\"SONORA\",\"clave\":\"SR\"},{\"ID_EntidadNacimiento\":\"27\",\"ID_Estado\":\"27\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"26\",\"Nombre\":\"TABASCO\",\"clave\":\"TC\"},{\"ID_EntidadNacimiento\":\"29\",\"ID_Estado\":\"28\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"27\",\"Nombre\":\"TLAXCALA\",\"clave\":\"TL\"},{\"ID_EntidadNacimiento\":\"28\",\"ID_Estado\":\"29\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"28\",\"Nombre\":\"TAMAULIPAS\",\"clave\":\"TS\"},{\"ID_EntidadNacimiento\":\"30\",\"ID_Estado\":\"30\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"29\",\"Nombre\":\"VERACRUZ\",\"clave\":\"VZ\"},{\"ID_EntidadNacimiento\":\"31\",\"ID_Estado\":\"31\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"30\",\"Nombre\":\"YUCATu00C1N\",\"clave\":\"YN\"},{\"ID_EntidadNacimiento\":\"32\",\"ID_Estado\":\"32\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"31\",\"Nombre\":\"ZACATECAS\",\"clave\":\"ZS\"},{\"ID_EntidadNacimiento\":\"33\",\"ID_Estado\":\"33\",\"ID_Estatus\":\"1\",\"IdEstadoFirebase\":\"32\",\"Nombre\":\"EXTRANJERO\",\"clave\":\"NE\"}]"

/* Activity For Result Request Code's */
const val RC_SEND_MONEY = 1000
const val RC_SCAN_QR = 1001

/* Firebase Project ID */
const val ODIN_ID = "odin-dd5ba"

/* Intent Action Names */
const val INTENT_TOKEN_FIREBASE = "INTENT_TOKEN_FIREBASE"
const val INTENT_PUSH_NOTIFICATION = "INTENT_PUSH_NOTIFICATION"

/* Longitud tipo de cuentas */
const val TARJETA_DEBITO = 16
const val TELEFONO_CELULAR = 10
const val CUENTA_CLABE = 18

/* Estados de operación CoDi */
const val PENDIENTE = 0
const val ACREDITADA = 1
const val RECHAZADA = 2
const val DEVUELTA = 3
