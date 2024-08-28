package com.froi.hotel.room.infrastructure.outputadapters;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "room_type", schema = "public")
@Getter
@Setter
@NoArgsConstructor
public class RoomTypeDbEntity {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column
    private String name;

    @Column(name = "maintenance_cost")
    private Double maintenanceCost;

    @Column(name = "capacity")
    private Integer capacity;
}
