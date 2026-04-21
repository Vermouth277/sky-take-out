package com.vermouth.vo;

import com.vermouth.entity.SetmealDish;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetmealExtraVO extends SetmealVO implements Serializable {
    private List<SetmealDish> setmealDishes = new ArrayList<>();
}
