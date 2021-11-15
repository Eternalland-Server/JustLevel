# JustLevel
> author: justwei

Zaphkiel item:
```yaml
__group__:
  material: EGG
  name: '境界消耗品'
stone_stage:
  display: DISPLAY_CONSUME
  icon: MAGMA_CREAM
  name:
    NAME: '&8&l[&3阶段突破石&8&l]'
  lore:
    TYPE: '消耗品'
    LORE:
      - '&7&o用于增加 &a段位&7&o 突破点'
      - ''
      - '&7[&2   右键   &7] &3使用一个'
      - '&7[&cSHIFT+右键&7] &3全部使用'
  data:
    eternal:
      ident: stage_stone
stone_realm:
  display: DISPLAY_CONSUME
  icon: ENDER_EYE
  name:
    NAME: '&8&l[&3境界突破石&8&l]'
  lore:
    TYPE: '消耗品'
    LORE:
      - '&7&o用于增加 &a境界&7&o 突破点'
      - ''
      - '&7[&2   右键   &7] &3使用一个'
      - '&7[&cSHIFT+右键&7] &3全部使用'
  data:
    eternal:
      ident: realm_stone
```

Placeholder:
```text
%justlevel_level% - 等级

%justlevel_total_level% - 总等级

%justlevel_exp% - 当前等级经验

%justlevel_upgrade_exp% - 当前等级升级所需经验

%justlevel_stage% - 当前阶段

%justlevel_realm% - 当前境界

%justlevel_realm_name% - 当前境界名称

%justlevel_stage_points% - 当前阶段突破点数

%justlevel_realm_points% - 当前境界突破点数
```