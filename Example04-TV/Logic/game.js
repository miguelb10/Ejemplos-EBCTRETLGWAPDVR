var game = new Phaser.Game(1920, 1080, Phaser.AUTO, 'phaser-example', { preload: preload, create: create});

function preload() {
    game.load.spritesheet('player', '../Assets/images/spriteExample.png', 90, 90, 20);
    game.load.image('nivel1', '../Assets/images/fondoMap.jpg');
}

var cursors;
var player;
var left;
var right;
var lastAction = "";
function create() {
    game.add.tileSprite(0, 0, 1920, 1080, 'nivel1');
    player = game.add.sprite(100, 100, 'player');
    player.smoothed = false;
    player.scale.set(1);

    left = player.animations.add('left', [4, 3, 2, 1, 0], 0, false);
    right = player.animations.add('right', [5, 6, 7, 8, 9], 9, false);
    player.animations.add('up', [10, 11, 12, 13, 14], 10, false);
    player.animations.add('down', [15, 16, 17, 18, 19], 10, false);

    game.physics.enable(player, Phaser.Physics.ARCADE);

}

function moveup() {
    player.body.velocity.y = -30;
    player.play('up');
    lastAction = "UP";
}

function movedown() {
    player.body.velocity.y = 30;
    player.play('down');
    lastAction = "DOWN";
}

function moveleft() {
    player.body.velocity.x = -30;
    player.play('left');
    lastAction = "LEFT";
}

function moveright() {
    player.body.velocity.x = 30;
    player.play('right');
    lastAction = "RIGHT";
}