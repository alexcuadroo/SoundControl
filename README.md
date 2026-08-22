# HardcoreSounds

Plugin para Paper 26.1.2 y 26.2 que reproduce sonidos personalizados incluidos en un resource pack estándar. Requiere Java 25; los clientes vanilla y Fabric no necesitan un mod específico.

## Compilar

```powershell
.\gradlew.bat clean test build
```

El JAR queda en `build/libs/HardcoreSounds-1.0.1.jar`.

## Añadir y publicar los sonidos

1. Coloca los archivos Ogg Vorbis en `resource-pack/source/assets/hardcoresounds/sounds/`. Los ocho sonidos iniciales ya están incluidos.
2. Si agregas otros sonidos, añade la misma clave a `sounds.json` y `sounds.yml`.
3. Ejecuta `powershell -File scripts/build-resource-packs.ps1`.
4. Publica ambos ZIP mediante URLs HTTP/HTTPS directas y permanentes.
5. Copia URL y SHA-1 a los perfiles anidados `profiles."26"."1"` y `profiles."26"."2"` de `config.yml`, conserva sus UUID y cambia `enabled` a `true`.

El ZIP 26.1 declara `min_format`/`max_format` 84 y el ZIP 26.2 declara 88.0. Versiones futuras de Minecraft requieren un perfil y pruebas explícitos.

## Comandos

- `/sfx` abre la GUI.
- `/sfx list`
- `/sfx play <sonido> [jugador|@a]`
- `/sfx stop <sonido> [jugador|@a]`
- `/sfx stopall [jugador|@a]`
- `/sfx reload`

La consola debe indicar un destino. Los permisos están definidos en `plugin.yml` y por defecto pertenecen a operadores.

## Notas de audio

Los sonidos mono son apropiados para audio posicional; los estéreo funcionan mejor como audio global. Este plugin implementa reproducción y detención, no streaming, seek, pausa real, fades ni sincronización musical.

Antes de publicar, verifica que tienes derecho a distribuir todos los audios. El pack viene desactivado y sin `.ogg` deliberadamente.

## Pruebas manuales

Probar el JAR tanto en Paper 26.1.2 como 26.2 con Java 25: entrada vanilla/Fabric, aceptación y rechazo de pack, todos los comandos y permisos, targets desconectados, paginación de ambas GUI, intentos de retirar ítems, cooldowns y recarga válida/inválida.
