var main = { // 이거 ㄹ한 이유? 즉 굳이 index라는 변수의 속성으로 function을 추가한 이유? index만의 스코프를 만들기 위해서. 브라우저의 스코프는 공용공간이기에 덮어쓰게 될 수있어서. index 객체 안에서만 function이 유효하기에.
    init :function () {
        var _this = this;

        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () { // btn-update란 id를 가진 html 엘리먼트에 click 이벤트 발생할 때 update function을 실행하도록 이벤트 등록
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },
    save : function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () { // 새로 추가된 update function
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT', // 여러 http method 중 put 메소드 선택. PostsApiController에 있는 API에서 이미 @PutMapping으로 했기에 PUT으로 해야함.
            url: '/api/v1/posts/'+id, // 어느 게시글을 수정할지 URL Path로 구분하기 위해 id 추가.
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();