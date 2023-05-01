package org.terasoluna.batch.tutorial.common.dto;

public class MemberInfoDto {
    private String id; 		// 会員番号に対応するフィールドとしてidを定義する。

    private String type; 	// 会員種別に対応するフィールドとしてtypeを定義する。

    private String status; 	// 商品購入フラグに対応するフィールドとしてstatusを定義する。

    private int point; 		// ポイントに対応するフィールドとしてpointを定義する。

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}